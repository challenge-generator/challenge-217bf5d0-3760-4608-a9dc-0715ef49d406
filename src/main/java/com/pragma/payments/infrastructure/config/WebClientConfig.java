package com.pragma.payments.infrastructure.config;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class WebClientConfig {

    private static final Logger log = LoggerFactory.getLogger(WebClientConfig.class);
    private static final String CORE_BANKING_BASE_URL = "http://localhost:8081/api/core-banking";
    private static final String FRAUD_DETECTION_BASE_URL = "http://localhost:8082/api/fraud-detection";
    private static final String RISK_ASSESSMENT_BASE_URL = "http://localhost:8083/api/risk-assessment";
    private static final String PAYMENT_GATEWAY_BASE_URL = "http://localhost:8084/api/payment-gateway";

    @Bean("coreBankingWebClient")
    public WebClient coreBankingWebClient() {
        return createWebClient(CORE_BANKING_BASE_URL);
    }

    @Bean("fraudDetectionWebClient")
    public WebClient fraudDetectionWebClient() {
        return createWebClient(FRAUD_DETECTION_BASE_URL);
    }

    @Bean("riskAssessmentWebClient")
    public WebClient riskAssessmentWebClient() {
        return createWebClient(RISK_ASSESSMENT_BASE_URL);
    }

    @Bean("paymentGatewayWebClient")
    public WebClient paymentGatewayWebClient() {
        return createWebClient(PAYMENT_GATEWAY_BASE_URL);
    }

    @Bean("webClient")
    public WebClient defaultWebClient() {
        return createWebClient(CORE_BANKING_BASE_URL);
    }

    private WebClient createWebClient(String baseUrl) {
        log.info("Creando WebClient con base URL: {}", baseUrl);

        ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(configurer -> configurer
                        .defaultCodecs()
                        .maxInMemorySize(16 * 1024 * 1024))
                .build();

        return WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.CONNECTION, "keep-alive")
                .exchangeStrategies(strategies)
                .clientConnector(new reactor.netty.http.client.HttpClient()
                        .option(io.netty.channel.ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                        .responseTimeout(Duration.ofSeconds(10))
                        .poolResources(
                                io.netty.channel.pool.FixedChannelPool.builder()
                                        .maxConnections(100)
                                        .pendingAcquireMaxCount(200)
                                        .pendingAcquireTimeout(Duration.ofSeconds(30))
                                        .build()
                        ))
                .filter((request, next) -> {
                    log.debug("Enviando request a: {} {}", request.method(), request.url());
                    return next.exchange(request)
                            .doOnTerminate(() -> log.debug("Request completado: {} {}", request.method(), request.url()))
                            .doOnError(error -> log.error("Error en request: {} {} - {}",
                                    request.method(), request.url(), error.getMessage()));
                })
                .build();
    }

    @Bean("coreBankingCircuitBreaker")
    public CircuitBreaker coreBankingCircuitBreaker() {
        return createCircuitBreaker("coreBanking", 3, 60, 10);
    }

    @Bean("fraudDetectionCircuitBreaker")
    public CircuitBreaker fraudDetectionCircuitBreaker() {
        return createCircuitBreaker("fraudDetection", 5, 30, 5);
    }

    @Bean("riskAssessmentCircuitBreaker")
    public CircuitBreaker riskAssessmentCircuitBreaker() {
        return createCircuitBreaker("riskAssessment", 5, 30, 5);
    }

    @Bean("paymentGatewayCircuitBreaker")
    public CircuitBreaker paymentGatewayCircuitBreaker() {
        return createCircuitBreaker("paymentGateway", 3, 120, 15);
    }

    private CircuitBreaker createCircuitBreaker(String name, int failureRateThreshold, int waitDurationInSeconds, int slidingWindowSize) {
        log.info("Configurando CircuitBreaker '{}': failureRateThreshold={}%, waitDuration={}s, slidingWindow={}",
                name, failureRateThreshold, waitDurationInSeconds, slidingWindowSize);

        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .failureRateThreshold(failureRateThreshold)
                .waitDurationInOpenState(Duration.ofSeconds(waitDurationInSeconds))
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
                .slidingWindowSize(slidingWindowSize)
                .minimumNumberOfCalls(10)
                .permittedNumberOfCallsInHalfOpenState(3)
                .automaticTransitionFromOpenToHalfOpenEnabled(true)
                .recordExceptions(java.io.IOException.class, java.util.concurrent.TimeoutException.class,
                        org.springframework.web.reactive.function.client.WebClientResponseException.class)
                .build();

        return CircuitBreaker.of(name, config);
    }

    @Bean
    public reactor.core.scheduler.Scheduler boundedElasticScheduler() {
        log.info("Configurando scheduler boundedElastic con pool personalizado");
        return Schedulers.boundedElastic(
                Thread.ofVirtual()
                        .name("webclient-bounded-", -1)
                        .factory(),
                200,
                60,
                java.util.concurrent.TimeUnit.SECONDS
        );
    }
}