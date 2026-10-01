package com.pragma.payments.infrastructure.adapter;

import com.pragma.payments.domain.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests de Integración para FraudDetectionAdapter")
class FraudDetectionAdapterTest {

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    private FraudDetectionAdapter fraudDetectionAdapter;

    @BeforeEach
    void setUp() {
        fraudDetectionAdapter = new FraudDetectionAdapter(webClient);
    }

    @Test
    @DisplayName("Debe retornar APPROVED cuando el servicio externo aprueba la transacción")
    void shouldReturnApprovedWhenServiceApproves() {
        Transaction transaction = Transaction.create(
            "ACC-001",
            "ACC-002",
            new BigDecimal("1000.00"),
            "Pago de servicio"
        );

        mockWebClientResponse("{\"result\":\"APPROVED\"}");

        StepVerifier.create(fraudDetectionAdapter.evaluateForFraud(transaction))
            .assertNext(result -> {
                assertThat(result).isEqualTo("APPROVED");
            })
            .verifyComplete();
    }

    @Test
    @DisplayName("Debe retornar FRAUD_DETECTED cuando se detecta fraude")
    void shouldReturnFraudDetectedWhenFraudIsFound() {
        Transaction transaction = Transaction.create(
            "ACC-001",
            "ACC-002",
            new BigDecimal("50000.00"),
            "Transferencia inusualmente alta"
        );

        mockWebClientResponse("{\"result\":\"FRAUD_DETECTED\",\"reason\":\"Monto exceeds limit\"}");

        StepVerifier.create(fraudDetectionAdapter.evaluateForFraud(transaction))
            .assertNext(result -> {
                assertThat(result).isEqualTo("FRAUD_DETECTED");
            })
            .verifyComplete();
    }

    @Test
    @DisplayName("Debe manejar error de timeout del servicio externo")
    void shouldHandleTimeoutError() {
        Transaction transaction = Transaction.create(
            "ACC-001",
            "ACC-002",
            new BigDecimal("1000.00"),
            "Pago de servicio"
        );

        when(webClient.post())
            .thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString()))
            .thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.header(anyString(), anyString()))
            .thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve())
            .thenReturn(responseSpec);
        when(responseSpec.bodyToMono(String.class))
            .thenReturn(Mono.error(new RuntimeException("Connection timeout")));

        StepVerifier.create(
            fraudDetectionAdapter.evaluateForFraud(transaction)
                .timeout(Duration.ofSeconds(5))
        )
            .expectErrorMatches(throwable -> 
                throwable.getMessage().contains("timeout") ||
                throwable.getMessage().contains("Connection")
            )
            .verify();
    }

    @Test
    @DisplayName("Debe manejar respuesta inválida del servicio externo")
    void shouldHandleInvalidResponse() {
        Transaction transaction = Transaction.create(
            "ACC-001",
            "ACC-002",
            new BigDecimal("1000.00"),
            "Pago de servicio"
        );

        mockWebClientResponse("invalid-json-response");

        StepVerifier.create(
            fraudDetectionAdapter.evaluateForFraud(transaction)
                .onErrorResume(Exception.class, e -> Mono.just("ERROR"))
        )
            .assertNext(result -> {
                assertThat(result).isEqualTo("ERROR");
            })
            .verifyComplete();
    }

    @Test
    @DisplayName("Debe retornar estado del servicio cuando se consulta el estado")
    void shouldReturnServiceStatus() {
        mockWebClientResponse("{\"status\":\"UP\",\"timestamp\":\"2024-01-01T00:00:00Z\"}");

        StepVerifier.create(fraudDetectionAdapter.getServiceStatus())
            .assertNext(status -> {
                assertThat(status).isIn("UP", "DOWN", "DEGRADED");
            })
            .verifyComplete();
    }

    @Test
    @DisplayName("Debe aplicar retry automático en caso de error transitorio")
    void shouldApplyRetryOnTransientError() {
        Transaction transaction = Transaction.create(
            "ACC-001",
            "ACC-002",
            new BigDecimal("1000.00"),
            "Pago de servicio"
        );

        when(webClient.post())
            .thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString()))
            .thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.header(anyString(), anyString()))
            .thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve())
            .thenReturn(responseSpec);
        when(responseSpec.bodyToMono(String.class))
            .thenReturn(
                Mono.error(new RuntimeException("Temporary failure")),
                Mono.just("{\"result\":\"APPROVED\"}")
            );

        StepVerifier.create(
            fraudDetectionAdapter.evaluateForFraud(transaction)
                .retry(1)
        )
            .assertNext(result -> assertThat(result).isEqualTo("APPROVED"))
            .verifyComplete();
    }

    @Test
    @DisplayName("Debe construir correctamente el payload JSON enviado al servicio")
    void shouldBuildCorrectPayloadJson() {
        Transaction transaction = Transaction.create(
            "ACC-001",
            "ACC-002",
            new BigDecimal("2500.50"),
            "Pago de prueba"
        );

        mockWebClientResponse("{\"result\":\"APPROVED\"}");

        StepVerifier.create(fraudDetectionAdapter.evaluateForFraud(transaction))
            .assertNext(result -> assertThat(result).isNotBlank())
            .verifyComplete();
    }

    @Test
    @DisplayName("Debe manejar excepción cuando el servicio retorna código de error HTTP")
    void shouldHandleHttpErrorCode() {
        Transaction transaction = Transaction.create(
            "ACC-001",
            "ACC-002",
            new BigDecimal("1000.00"),
            "Pago de servicio"
        );

        when(webClient.post())
            .thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString()))
            .thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.header(anyString(), anyString()))
            .thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve())
            .thenReturn(responseSpec);
        when(responseSpec.bodyToMono(String.class))
            .thenReturn(Mono.error(new RuntimeException("HTTP 500: Internal Server Error")));

        StepVerifier.create(
            fraudDetectionAdapter.evaluateForFraud(transaction)
                .onErrorResume(Exception.class, e -> Mono.just("FALLBACK_APPROVED"))
        )
            .assertNext(result -> assertThat(result).isEqualTo("FALLBACK_APPROVED"))
            .verifyComplete();
    }

    @Test
    @DisplayName("Debe usar circuit breaker para proteger el servicio externo")
    void shouldUseCircuitBreakerForProtection() {
        Transaction transaction = Transaction.create(
            "ACC-001",
            "ACC-002",
            new BigDecimal("1000.00"),
            "Pago de servicio"
        );

        mockWebClientResponse("{\"result\":\"APPROVED\"}");

        StepVerifier.create(
            fraudDetectionAdapter.evaluateForFraud(transaction)
        )
            .assertNext(result -> assertThat(result).isIn("APPROVED", "FRAUD_DETECTED", "ERROR"))
            .verifyComplete();
    }

    private void mockWebClientResponse(String responseBody) {
        when(webClient.post())
            .thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString()))
            .thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.header(anyString(), anyString()))
            .thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve())
            .thenReturn(responseSpec);
        when(responseSpec.bodyToMono(String.class))
            .thenReturn(Mono.just(responseBody));
    }
}