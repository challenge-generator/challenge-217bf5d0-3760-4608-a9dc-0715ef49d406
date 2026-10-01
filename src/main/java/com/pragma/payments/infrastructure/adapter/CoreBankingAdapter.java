package com.pragma.payments.infrastructure.adapter;

import com.pragma.payments.domain.model.Transaction;
import com.pragma.payments.domain.port.CoreBankingService;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.operator.CircuitBreakerOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class CoreBankingAdapter implements CoreBankingService {

    private static final Logger log = LoggerFactory.getLogger(CoreBankingAdapter.class);
    private static final String CORE_BANKING_BASE_URL = "http://localhost:8081/api/core-banking";
    private static final String PROCESS_TRANSACTION_ENDPOINT = "/process";
    private static final String GET_ACCOUNT_ENDPOINT = "/account";
    private static final String VERIFY_BALANCE_ENDPOINT = "/verify-balance";

    private final WebClient webClient;
    private final CircuitBreaker circuitBreaker;

    public CoreBankingAdapter(
            @Qualifier("webClient") WebClient webClient,
            @Qualifier("circuitBreaker") CircuitBreaker circuitBreaker) {
        this.webClient = webClient;
        this.circuitBreaker = circuitBreaker;
    }

    @Override
    public Mono<String> processTransaction(Transaction transaction) {
        log.info("Procesando transacción en core bancario: {}", transaction.id());

        return webClient
                .post()
                .uri(CORE_BANKING_BASE_URL + PROCESS_TRANSACTION_ENDPOINT)
                .bodyValue(buildCoreBankingRequest(transaction))
                .retrieve()
                .bodyToMono(String.class)
                .transformDeferred(CircuitBreakerOperator.of(circuitBreaker))
                .doOnSuccess(response -> log.info("Transacción procesada exitosamente en core: {}", response))
                .doOnError(error -> log.error("Error al procesar transacción en core bancario: {}", error.getMessage()))
                .onErrorResume(error -> handleCoreBankingError(error, transaction));
    }

    @Override
    public Mono<String> getAccountStatus(String accountNumber) {
        log.info("Consultando estado de cuenta en core bancario: {}", accountNumber);

        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(CORE_BANKING_BASE_URL + GET_ACCOUNT_ENDPOINT)
                        .queryParam("account", accountNumber)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .transformDeferred(CircuitBreakerOperator.of(circuitBreaker))
                .doOnSuccess(response -> log.info("Estado de cuenta consultado: {}", response))
                .doOnError(error -> log.error("Error al consultar cuenta: {}", error.getMessage()))
                .onErrorResume(error -> {
                    log.warn("Fallback al consultar cuenta {}: {}", accountNumber, error.getMessage());
                    return Mono.just("ACCOUNT_STATUS_UNAVAILABLE");
                });
    }

    @Override
    public Mono<Boolean> verifySufficientBalance(String accountNumber, java.math.BigDecimal amount) {
        log.info("Verificando saldo suficiente para cuenta: {}, monto: {}", accountNumber, amount);

        return webClient
                .post()
                .uri(CORE_BANKING_BASE_URL + VERIFY_BALANCE_ENDPOINT)
                .bodyValue(buildBalanceVerificationRequest(accountNumber, amount))
                .retrieve()
                .bodyToMono(Boolean.class)
                .transformDeferred(CircuitBreakerOperator.of(circuitBreaker))
                .doOnSuccess(verified -> log.info("Verificación de saldo completada: cuenta={}, monto={}, resultado={}",
                        accountNumber, amount, verified))
                .doOnError(error -> log.error("Error al verificar saldo: {}", error.getMessage()))
                .onErrorResume(error -> {
                    log.warn("Fallback al verificar saldo para {}: {}", accountNumber, error.getMessage());
                    return Mono.just(false);
                });
    }

    private CoreBankingRequest buildCoreBankingRequest(Transaction transaction) {
        return new CoreBankingRequest(
                transaction.id().toString(),
                transaction.accountOrigin(),
                transaction.accountDestination(),
                transaction.amount().toString(),
                transaction.currency(),
                transaction.type().name()
        );
    }

    private BalanceVerificationRequest buildBalanceVerificationRequest(String accountNumber, java.math.BigDecimal amount) {
        return new BalanceVerificationRequest(accountNumber, amount.toString());
    }

    private Mono<String> handleCoreBankingError(Throwable error, Transaction transaction) {
        if (error instanceof io.github.resilience4j.circuitbreaker.CallNotPermittedException) {
            log.warn("Circuit breaker abierto para core bancario, usando fallback para transacción: {}", transaction.id());
            return Mono.just("CORE_BANKING_CIRCUIT_OPEN");
        }
        return Mono.just("CORE_BANKING_ERROR: " + error.getMessage());
    }

    private record CoreBankingRequest(
            String transactionId,
            String accountOrigin,
            String accountDestination,
            String amount,
            String currency,
            String transactionType
    ) {}

    private record BalanceVerificationRequest(
            String accountNumber,
            String amount
    ) {}
}