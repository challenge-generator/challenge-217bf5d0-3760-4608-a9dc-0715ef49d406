package com.pragma.payments.infrastructure.controller;

import com.pragma.payments.application.service.TransactionService;
import com.pragma.payments.domain.model.TransactionRequest;
import com.pragma.payments.domain.model.TransactionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private static final Logger log = LoggerFactory.getLogger(TransactionController.class);

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public Mono<ResponseEntity<TransactionResponse>> createTransaction(@RequestBody TransactionRequest request) {
        log.info("Recibida solicitud de transacción: origen={}, destino={}, monto={}",
                request.accountOrigin(), request.accountDestination(), request.amount());

        return transactionService.processTransaction(request)
                .map(transaction -> {
                    log.info("Transacción procesada exitosamente: id={}, estado={}",
                            transaction.id(), transaction.status());
                    return ResponseEntity
                            .status(HttpStatus.CREATED)
                            .body(TransactionResponse.fromTransaction(transaction));
                })
                .doOnError(error -> log.error("Error al procesar transacción: {}", error.getMessage()))
                .onErrorResume(error -> {
                    log.error("Error en createTransaction: {}", error.getMessage());
                    return Mono.just(ResponseEntity
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(TransactionResponse.error(error.getMessage())));
                });
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<TransactionResponse>> getTransactionById(@PathVariable UUID id) {
        log.info("Consultando transacción por ID: {}", id);

        return transactionService.findById(id)
                .map(transaction -> {
                    log.info("Transacción encontrada: id={}, estado={}", id, transaction.status());
                    return ResponseEntity.ok(TransactionResponse.fromTransaction(transaction));
                })
                .defaultIfEmpty(ResponseEntity.notFound().build())
                .doOnError(error -> log.error("Error al buscar transacción {}: {}", id, error.getMessage()));
    }

    @GetMapping("/account/{accountOrigin}")
    public Flux<TransactionResponse> getTransactionsByAccountOrigin(@PathVariable String accountOrigin) {
        log.info("Consultando transacciones por cuenta origen: {}", accountOrigin);

        return transactionService.findByAccountOrigin(accountOrigin)
                .doOnSubscribe(s -> log.debug("Iniciandoflux de transacciones para cuenta: {}", accountOrigin))
                .doOnComplete(() -> log.debug("Completado flujo de transacciones para cuenta: {}", accountOrigin))
                .doOnError(error -> log.error("Error al buscar transacciones por cuenta {}: {}",
                        accountOrigin, error.getMessage()))
                .map(TransactionResponse::fromTransaction);
    }

    @GetMapping("/account-destination/{accountDestination}")
    public Flux<TransactionResponse> getTransactionsByAccountDestination(@PathVariable String accountDestination) {
        log.info("Consultando transacciones por cuenta destino: {}", accountDestination);

        return transactionService.findByAccountDestination(accountDestination)
                .doOnSubscribe(s -> log.debug("Iniciando flujo de transacciones para cuenta destino: {}", accountDestination))
                .doOnComplete(() -> log.debug("Completado flujo de transacciones para cuenta destino: {}", accountDestination))
                .doOnError(error -> log.error("Error al buscar transacciones por cuenta destino {}: {}",
                        accountDestination, error.getMessage()))
                .map(TransactionResponse::fromTransaction);
    }

    @GetMapping("/date-range")
    public Flux<TransactionResponse> getTransactionsByDateRange(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        log.info("Consultando transacciones entre {} y {}", startDate, endDate);

        return transactionService.findByCreatedAtBetween(startDate, endDate)
                .doOnSubscribe(s -> log.debug("Iniciando flujo de transacciones por rango de fechas"))
                .doOnComplete(() -> log.debug("Completado flujo de transacciones por rango de fechas"))
                .doOnError(error -> log.error("Error al buscar transacciones por fecha: {}", error.getMessage()))
                .map(TransactionResponse::fromTransaction);
    }

    @PatchMapping("/{id}/status")
    public Mono<ResponseEntity<TransactionResponse>> updateTransactionStatus(
            @PathVariable UUID id,
            @RequestParam String status) {
        log.info("Actualizando estado de transacción {} a {}", id, status);

        return transactionService.updateStatus(id, status)
                .map(transaction -> {
                    log.info("Estado de transacción {} actualizado a {}", id, status);
                    return ResponseEntity.ok(TransactionResponse.fromTransaction(transaction));
                })
                .defaultIfEmpty(ResponseEntity.notFound().build())
                .doOnError(error -> log.error("Error al actualizar estado de transacción {}: {}", id, error.getMessage()));
    }

    @GetMapping("/health")
    public Mono<ResponseEntity<HealthResponse>> healthCheck() {
        log.debug("Verificando salud del servicio de transacciones");

        return transactionService.getServiceStatus()
                .map(status -> ResponseEntity.ok(new HealthResponse("UP", status)))
                .onErrorResume(error -> {
                    log.warn("Health check falló: {}", error.getMessage());
                    return Mono.just(ResponseEntity.ok(new HealthResponse("DEGRADED", "Servicios externos no disponibles")));
                });
    }

    public record HealthResponse(String status, String message) {}
}