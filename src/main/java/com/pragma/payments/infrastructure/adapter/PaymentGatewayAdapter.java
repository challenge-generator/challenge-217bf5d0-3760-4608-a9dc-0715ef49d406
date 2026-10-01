package com.pragma.payments.infrastructure.adapter;

import com.pragma.payments.domain.model.Transaction;
import com.pragma.payments.domain.port.PaymentGatewayService;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.bulkhead.Bulkhead;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class PaymentGatewayAdapter implements PaymentGatewayService {

    private static final Logger log = LoggerFactory.getLogger(PaymentGatewayAdapter.class);
    private static final String GATEWAY_BASE_URL = "http://payment-gateway-service:8080";

    private final WebClient webClient;
    private final CircuitBreaker circuitBreaker;
    private final Retry retry;
    private final Bulkhead bulkhead;

    public PaymentGatewayAdapter(
            WebClient webClient,
            @Qualifier("paymentCircuitBreaker") CircuitBreaker circuitBreaker,
            @Qualifier("paymentRetry") Retry retry,
            @Qualifier("paymentBulkhead") Bulkhead bulkhead) {
        this.webClient = webClient;
        this.circuitBreaker = circuitBreaker;
        this.retry = retry;
        this.bulkhead = bulkhead;
    }

    @Override
    public Mono<String> processPayment(Transaction transaction) {
        return Mono.fromCallable(() -> bulkhead.executeSupplier(() -> ""))
                .transformDeferred(it -> Retry.decorateRetryMono(retry, it))
                .transformDeferred(it -> CircuitBreaker.decorateMono(circuitBreaker, it))
                .flatMap(result -> executePaymentRequest(transaction))
                .doOnSuccess(response -> log.info("Pago procesado exitosamente para transacción: {}", transaction.id()))
                .doOnError(error -> log.error("Error al procesar pago para transacción: {}", transaction.id(), error))
                .onErrorResume(Exception.class, error -> Mono.just("PAYMENT_FAILED:" + error.getMessage()));
    }

    private Mono<String> executePaymentRequest(Transaction transaction) {
        PaymentRequest request = new PaymentRequest(
                transaction.accountOrigin(),
                transaction.accountDestination(),
                transaction.amount()
        );

        return webClient.post()
                .uri(GATEWAY_BASE_URL + "/api/payments")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(PaymentResponse.class)
                .map(response -> {
                    if ("APPROVED".equals(response.status())) {
                        return "PAYMENT_APPROVED:" + response.transactionId();
                    } else {
                        return "PAYMENT_DECLINED:" + response.reason();
                    }
                })
                .onErrorResume(WebClientException.class, error -> {
                    log.error("Error de comunicación con gateway de pagos", error);
                    return Mono.just("PAYMENT_ERROR:CONNECTION_FAILED");
                });
    }

    @Override
    public Mono<String> getPaymentStatus(String paymentId) {
        return Mono.fromCallable(() -> bulkhead.executeSupplier(() -> ""))
                .transformDeferred(it -> Retry.decorateRetryMono(retry, it))
                .transformDeferred(it -> CircuitBreaker.decorateMono(circuitBreaker, it))
                .flatMap(result -> executeStatusRequest(paymentId))
                .doOnSuccess(response -> log.info("Estado de pago consultado: {}", paymentId))
                .doOnError(error -> log.error("Error al consultar estado de pago: {}", paymentId, error))
                .onErrorResume(Exception.class, error -> Mono.just("STATUS_ERROR:" + error.getMessage()));
    }

    private Mono<String> executeStatusRequest(String paymentId) {
        return webClient.get()
                .uri(GATEWAY_BASE_URL + "/api/payments/{paymentId}", paymentId)
                .retrieve()
                .bodyToMono(PaymentResponse.class)
                .map(response -> response.status() + ":" + response.transactionId())
                .onErrorResume(WebClientException.class, error -> {
                    log.error("Error al consultar estado del pago", error);
                    return Mono.just("STATUS_ERROR:CONNECTION_FAILED");
                });
    }

    private record PaymentRequest(String originAccount, String destinationAccount, java.math.BigDecimal amount) {}

    private record PaymentResponse(String transactionId, String status, String reason) {}
}