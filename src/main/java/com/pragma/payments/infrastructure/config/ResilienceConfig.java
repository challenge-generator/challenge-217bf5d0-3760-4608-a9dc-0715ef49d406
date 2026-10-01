package com.pragma.payments.infrastructure.config;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.BulkheadConfig;
import io.github.resilience4j.bulkhead.BulkheadRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class ResilienceConfig {

    @Bean
    public CircuitBreakerRegistry circuitBreakerRegistry() {
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofSeconds(60))
                .slidingWindowSize(10)
                .minimumNumberOfCalls(5)
                .permittedNumberOfCallsInHalfOpenState(3)
                .automaticTransitionFromOpenToHalfOpenEnabled(true)
                .build();
        return CircuitBreakerRegistry.of(config);
    }

    @Bean
    public CircuitBreaker paymentCircuitBreaker(CircuitBreakerRegistry circuitBreakerRegistry) {
        return circuitBreakerRegistry.circuitBreaker("paymentGateway");
    }

    @Bean
    public CircuitBreaker fraudDetectionCircuitBreaker(CircuitBreakerRegistry circuitBreakerRegistry) {
        return circuitBreakerRegistry.circuitBreaker("fraudDetection");
    }

    @Bean
    public CircuitBreaker riskAssessmentCircuitBreaker(CircuitBreakerRegistry circuitBreakerRegistry) {
        return circuitBreakerRegistry.circuitBreaker("riskAssessment");
    }

    @Bean
    public CircuitBreaker coreBankingCircuitBreaker(CircuitBreakerRegistry circuitBreakerRegistry) {
        return circuitBreakerRegistry.circuitBreaker("coreBanking");
    }

    @Bean
    public RetryRegistry retryRegistry() {
        RetryConfig config = RetryConfig.custom()
                .maxAttempts(3)
                .waitDuration(Duration.ofMillis(500))
                .ignoreExceptions(Exception.class)
                .build();
        return RetryRegistry.of(config);
    }

    @Bean
    public Retry paymentRetry(RetryRegistry retryRegistry) {
        return retryRegistry.retry("paymentGatewayRetry");
    }

    @Bean
    public Retry fraudDetectionRetry(RetryRegistry retryRegistry) {
        return retryRegistry.retry("fraudDetectionRetry");
    }

    @Bean
    public Retry riskAssessmentRetry(RetryRegistry retryRegistry) {
        return retryRegistry.retry("riskAssessmentRetry");
    }

    @Bean
    public Retry coreBankingRetry(RetryRegistry retryRegistry) {
        return retryRegistry.retry("coreBankingRetry");
    }

    @Bean
    public BulkheadRegistry bulkheadRegistry() {
        BulkheadConfig config = BulkheadConfig.custom()
                .maxConcurrentCalls(100)
                .maxWaitDuration(Duration.ofMillis(500))
                .build();
        return BulkheadRegistry.of(config);
    }

    @Bean
    public Bulkhead paymentBulkhead(BulkheadRegistry bulkheadRegistry) {
        return bulkheadRegistry.bulkhead("paymentGatewayBulkhead");
    }

    @Bean
    public Bulkhead fraudDetectionBulkhead(BulkheadRegistry bulkheadRegistry) {
        return bulkheadRegistry.bulkhead("fraudDetectionBulkhead");
    }

    @Bean
    public Bulkhead riskAssessmentBulkhead(BulkheadRegistry bulkheadRegistry) {
        return bulkheadRegistry.bulkhead("riskAssessmentBulkhead");
    }

    @Bean
    public Bulkhead coreBankingBulkhead(BulkheadRegistry bulkheadRegistry) {
        return bulkheadRegistry.bulkhead("coreBankingBulkhead");
    }
}