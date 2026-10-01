package com.pragma.payments.infrastructure.adapter;

import com.pragma.payments.domain.model.Transaction;
import com.pragma.payments.domain.port.RiskAssessmentService;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import io.github.resilience4j.reactor.retry.operator.RetryOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;

@Service
public class RiskAssessmentAdapter implements RiskAssessmentService {

    private static final Logger log = LoggerFactory.getLogger(RiskAssessmentAdapter.class);
    private static final String SERVICE_NAME = "risk-assessment";
    private static final String LOW_RISK = "LOW_RISK";
    private static final String MEDIUM_RISK = "MEDIUM_RISK";
    private static final String HIGH_RISK = "HIGH_RISK";
    private static final String UNKNOWN = "UNKNOWN";

    private final WebClient webClient;
    private final CircuitBreaker circuitBreaker;
    private final Retry retry;

    public RiskAssessmentAdapter(WebClient webClient, 
                                  CircuitBreakerRegistry circuitBreakerRegistry,
                                  RetryRegistry retryRegistry) {
        this.webClient = webClient;
        this.circuitBreaker = initCircuitBreaker(circuitBreakerRegistry);
        this.retry = initRetry(retryRegistry);
    }

    private CircuitBreaker initCircuitBreaker(CircuitBreakerRegistry registry) {
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
            .failureRateThreshold(40)
            .waitDurationInOpenState(Duration.ofSeconds(45))
            .slidingWindowSize(8)
            .minimumNumberOfCalls(4)
            .permittedNumberOfCallsInHalfOpenState(2)
            .automaticTransitionFromOpenToHalfOpenEnabled(true)
            .build();
        
        return registry.circuitBreaker(SERVICE_NAME, config);
    }

    private Retry initRetry(RetryRegistry registry) {
        RetryConfig config = RetryConfig.custom()
            .maxAttempts(3)
            .waitDuration(Duration.ofMillis(500))
            .retryExceptions(RuntimeException.class)
            .ignoreExceptions(IllegalArgumentException.class)
            .build();
        
        return registry.retry(SERVICE_NAME, config);
    }

    @Override
    public Mono<String> assessRisk(Transaction transaction) {
        log.info("Assessing risk for transaction {}", transaction.id());
        
        Map<String, Object> requestBody = Map.of(
            "transactionId", transaction.id().toString(),
            "accountOrigin", transaction.accountOrigin(),
            "accountDestination", transaction.accountDestination(),
            "amount", transaction.amount().doubleValue(),
            "currency", transaction.currency(),
            "fraudResult", transaction.fraudResult() != null ? transaction.fraudResult() : "PENDING"
        );

        return webClient.post()
            .uri("/api/v1/risk/assess")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(requestBody)
            .retrieve()
            .bodyToMono(Map.class)
            .timeout(Duration.ofMillis(180), Mono.error(
                new RiskAssessmentTimeoutException("Risk assessment service timeout")))
            .retryWhen(RetryOperator.of(retry))
            .map(response -> parseRiskResponse(response, transaction.id().toString()))
            .transformDeferred(CircuitBreakerOperator.of(circuitBreaker))
            .doOnSuccess(result -> log.info("Risk assessment for {} completed: {}", 
                transaction.id(), result))
            .doOnError(e -> log.error("Risk assessment failed for {}: {}", 
                transaction.id(), e.getMessage()))
            .onErrorResume(RiskAssessmentTimeoutException.class, e -> {
                log.warn("Risk assessment timeout for {}, applying fallback", transaction.id());
                return applyFallback(transaction);
            })
            .onErrorResume(Exception.class, e -> {
                log.error("Unexpected error in risk assessment for {}", transaction.id(), e);
                return applyFallback(transaction);
            });
    }

    private Mono<String> applyFallback(Transaction transaction) {
        log.info("Applying risk assessment fallback for transaction {}", transaction.id());
        java.math.BigDecimal amount = transaction.amount();
        
        if (amount.compareTo(java.math.BigDecimal.valueOf(5000)) <= 0) {
            return Mono.just(LOW_RISK);
        } else if (amount.compareTo(java.math.BigDecimal.valueOf(20000)) <= 0) {
            return Mono.just(MEDIUM_RISK);
        } else {
            return Mono.just(HIGH_RISK);
        }
    }

    private String parseRiskResponse(Map<String, Object> response, String transactionId) {
        if (response == null) {
            log.warn("Empty risk response for {}, defaulting to MEDIUM_RISK", transactionId);
            return MEDIUM_RISK;
        }

        Object level = response.get("riskLevel");
        if (level == null) {
            Object score = response.get("riskScore");
            if (score != null) {
                return mapScoreToRiskLevel(score, transactionId);
            }
            log.warn("No riskLevel or riskScore in response for {}, defaulting to MEDIUM_RISK", 
                transactionId);
            return MEDIUM_RISK;
        }

        String levelStr = level.toString().toUpperCase();
        return switch (levelStr) {
            case "LOW", "LOW_RISK", "MINIMAL", "NEGLIGIBLE" -> LOW_RISK;
            case "MEDIUM", "MEDIUM_RISK", "MODERATE" -> MEDIUM_RISK;
            case "HIGH", "HIGH_RISK", "ELEVATED", "CRITICAL" -> HIGH_RISK;
            default -> MEDIUM_RISK;
        };
    }

    private String mapScoreToRiskLevel(Object score, String transactionId) {
        try {
            double numericScore = Double.parseDouble(score.toString());
            if (numericScore <= 30) {
                return LOW_RISK;
            } else if (numericScore <= 70) {
                return MEDIUM_RISK;
            } else {
                return HIGH_RISK;
            }
        } catch (NumberFormatException e) {
            log.warn("Invalid risk score format for {}: {}, defaulting to MEDIUM_RISK", 
                transactionId, score);
            return MEDIUM_RISK;
        }
    }

    @Override
    public Mono<String> getServiceStatus() {
        return webClient.get()
            .uri("/api/v1/risk/health")
            .retrieve()
            .bodyToMono(Map.class)
            .map(response -> {
                Object status = response.get("status");
                return status != null ? status.toString() : UNKNOWN;
            })
            .onErrorResume(e -> Mono.just("UNAVAILABLE"))
            .defaultIfEmpty(UNKNOWN);
    }

    public static class RiskAssessmentTimeoutException extends RuntimeException {
        public RiskAssessmentTimeoutException(String message) {
            super(message);
        }
    }
}