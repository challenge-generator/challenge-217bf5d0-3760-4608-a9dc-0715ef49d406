package com.pragma.payments.domain.port;

import com.pragma.payments.domain.model.Transaction;
import reactor.core.publisher.Mono;

/**
 * Puerto para el servicio del core bancario.
 * Define la interfaz que el dominio necesita para interactuar con el core bancario.
 */
public interface CoreBankingService {

    /**
     * Procesa una transacción en el core bancario.
     * @param transaction La transacción a procesar
     * @return Mono que emite el resultado del procesamiento
     */
    Mono<String> processTransaction(Transaction transaction);

    /**
     * Consulta el saldo de una cuenta.
     * @param accountNumber Número de cuenta a consultar
     * @return Mono que emite el saldo actual
     */
    Mono<java.math.BigDecimal> getAccountBalance(String accountNumber);

    /**
     * Reserva fondos para una transacción.
     * @param accountNumber Cuenta origen
     * @param amount Monto a reservar
     * @return Mono que emite true si la reserva fue exitosa
     */
    Mono<Boolean> reserveFunds(String accountNumber, java.math.BigDecimal amount);

    /**
     * Confirma la transferencia de fondos.
     * @param transactionId ID de la transacción
     * @return Mono que emite true si la confirmación fue exitosa
     */
    Mono<Boolean> confirmTransfer(java.util.UUID transactionId);

    /**
     * Revierte una transacción previamente confirmada.
     * @param transactionId ID de la transacción a revertir
     * @return Mono que emite true si la reversa fue exitosa
     */
    Mono<Boolean> reverseTransaction(java.util.UUID transactionId);

    /**
     * Obtiene el estado del servicio del core bancario.
     * @return Mono que emite el estado del servicio
     */
    Mono<String> getServiceStatus();
}