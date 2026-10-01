package com.pragma.payments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Hooks;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator;
import java.time.Duration;

@SpringBootApplication
public class PaymentsApplication {

    public static void main(String[] args) {
        Hooks.onOperatorDebug();
        SpringApplication.run(PaymentsApplication.class, args);
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8080")
                .build();
    }

    @Bean
    public CircuitBreakerConfig customCircuitBreakerConfig() {
        return CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofMillis(1000))
                .permittedNumberOfCallsInHalfOpenState(3)
                .slidingWindowSize(5)
                .recordExceptions(
                    org.springframework.web.reactive.function.client.WebClientResponseException.class,
                    java.util.concurrent.TimeoutException.class,
                    io.netty.handler.timeout.TimeoutException.class
                )
                .build();
    }

    @Bean
    public CircuitBreaker circuitBreaker(CircuitBreakerConfig customCircuitBreakerConfig) {
        return CircuitBreaker.of("paymentCircuitBreaker", customCircuitBreakerConfig);
    }

    @Bean
    public CircuitBreakerOperator<Object> circuitBreakerOperator(CircuitBreaker circuitBreaker) {
        return CircuitBreakerOperator.of(circuitBreaker);
    }
}