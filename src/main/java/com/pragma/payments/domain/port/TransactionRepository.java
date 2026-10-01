package com.pragma.payments.domain.port;

import com.pragma.payments.domain.model.Transaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Puerto de persistencia para operaciones sobre transacciones.
 * Define las operaciones básicas de creación, lectura y actualización
 * que deben ser implementadas por los adaptadores de infraestructura.
 */
public interface TransactionRepository {
    /**
     * Guarda una transacción en el repositorio.
     * @param transaction La transacción a guardar
     * @return Mono con la transacción guardada
     */
    Mono<Transaction> save(Transaction transaction);

    /**
     * Busca una transacción por su ID.
     * @param id El ID de la transacción
     * @return Mono con la transacción encontrada, o vacío si no existe
     */
    Mono<Transaction> findById(UUID id);

    /**
     * Busca todas las transacciones asociadas a una cuenta de origen.
     * @param accountOrigin La cuenta de origen
     * @return Flux con las transacciones encontradas
     */
    Flux<Transaction> findByAccountOrigin(String accountOrigin);

    /**
     * Busca todas las transacciones asociadas a una cuenta de destino.
     * @param accountDestination La cuenta de destino
     * @return Flux con las transacciones encontradas
     */
    Flux<Transaction> findByAccountDestination(String accountDestination);

    /**
     * Actualiza el estado de una transacción.
     * @param id El ID de la transacción
     * @param status El nuevo estado
     * @return Mono con la transacción actualizada
     */
    Mono<Transaction> updateStatus(UUID id, String status);

    /**
     * Busca transacciones por rango de fechas de creación.
     * @param startDate Fecha de inicio
     * @param endDate Fecha de fin
     * @return Flux con las transacciones encontradas
     */
    Flux<Transaction> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
}