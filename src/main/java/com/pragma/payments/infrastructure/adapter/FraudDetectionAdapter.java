package com.pragma.payments.infrastructure.adapter;

import com.pragma.payments.domain.model.Transaction;
import com.pragma.payments.domain.port.FraudDetectionService;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;
import java.util.function.Function;

@Service
public class FraudDetectionAdapter implements FraudDetectionService {

    private static final Logger log = LoggerFactory.getLogger(FraudDetectionAdapter.class);
    private static final String SERVICE_NAME = "fraud-detection";
    private static final String APPROVED = "APPROVED";
    private static final String REJECTED = "REJECTED";
    private static final String REVIEW = "REVIEW";

    private final WebClient webClient;
    private final CircuitBreaker circuitBreaker;
    private final CircuitBreakerRegistry circuitBreakerRegistry;

    public FraudDetectionAdapter(WebClient webClient, CircuitBreakerRegistry circuitBreakerRegistry) {
        this.webClient = webClient;
        this.circuitBreakerRegistry = circuitBreakerRegistry;
        this.circuitBreaker = initCircuitBreaker();
    }

    private CircuitBreaker initCircuitBreaker() {
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
            .failureRateThreshold(50)
            .waitDurationInOpenState(Duration.ofSeconds(30))
            .slidingWindowSize(10)
            .minimumNumberOfCalls(5)
            .permittedNumberOfCallsInHalfOpenState(3)
            .automaticTransitionFromOpenToHalfOpenEnabled(true)
            .build();
        
        return circuitBreakerRegistry.circuitBreaker(SERVICE_NAME, config);
    }

    @Override
    public Mono<String> evaluateForFraud(Transaction transaction) {
        log.info("Evaluating transaction {} for fraud", transaction.id());
        
        Map<String, Object> requestBody = Map.of(
            "transactionId", transaction.id().toString(),
            "accountOrigin", transaction.accountOrigin(),
            "accountDestination", transaction.accountDestination(),
            "amount", transaction.amount().doubleValue(),
            "currency", transaction.currency()
        );

        return webClient.post()
            .uri("/api/v1/fraud/evaluate")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(requestBody)
            .retrieve()
            .bodyToMono(Map.class)
            .timeout(Duration.ofMillis(150), Mono.error(
                new FraudDetectionTimeoutException("Fraud detection service timeout")))
            .map(response -> parseFraudResponse(response, transaction.id().toString()))
            .transformDeferred(CircuitBreakerOperator.of(circuitBreaker))
            .doOnSuccess(result -> log.info("Fraud evaluation for {} completed: {}", 
                transaction.id(), result))
            .doOnError(e -> log.error("Fraud evaluation failed for {}: {}", 
                transaction.id(), e.getMessage()))
            .onErrorResume(FraudDetectionTimeoutException.class, e -> {
                log.warn("Fraud detection timeout for {}, applying fallback", transaction.id());
                return applyFallback(transaction);
            })
            .onErrorResume(CircuitBreakerOpenException.class, e -> {
                log.warn("Circuit breaker open for fraud detection, applying fallback");
                return applyFallback(transaction);
            })
            .onErrorResume(Exception.class, e -> {
                log.error("Unexpected error in fraud detection for {}", transaction.id(), e);
                return Mono.just(REVIEW);
            });
    }

    private Mono<String> applyFallback(Transaction transaction) {
        log.info("Applying fraud detection fallback for transaction {}", transaction.id());
        if (transaction.amount().compareTo(java.math.BigDecimal.valueOf(10000)) > 0) {
            return Mono.just(REVIEW);
        }
        return Mono.just(APPROVED);
    }

    private String parseFraudResponse(Map<String, Object> response, String transactionId) {
        if (response == null) {
            log.warn("Empty fraud response for {}, defaulting to REVIEW", transactionId);
            return REVIEW;
        }

        Object result = response.get("result");
        if (result == null) {
            log.warn("No result field in fraud response for {}, defaulting to REVIEW", transactionId);
            return REVIEW;
        }

        String resultStr = result.toString().toUpperCase();
        return switch (resultStr) {
            case "APPROVE", "APPROVED", "LOW_RISK", "CLEAR" -> APPROVED;
            case "REJECT", "REJECTED", "HIGH_RISK", "FRAUD" -> REJECTED;
            default -> REVIEW;
        };
    }

    @Override
    public Mono<String> getServiceStatus() {
        return webClient.get()
            .uri("/api/v1/fraud/health")
            .retrieve()
            .bodyToMono(Map.class)
            .map(response -> {
                Object status = response.get("status");
                return status != null ? status.toString() : "UNKNOWN";
            })
            .onErrorResume(e -> Mono.just("UNAVAILABLE"))
            .defaultIfEmpty("UNKNOWN");
    }

    public static class FraudDetectionTimeoutException extends RuntimeException {
        public FraudDetectionTimeoutException(String message) {
            super(message);
        }
    }

    public static class CircuitBreakerOpenException extends RuntimeException {
        public CircuitBreakerOpenException(String message) {
            super(message);
        }
    }
}