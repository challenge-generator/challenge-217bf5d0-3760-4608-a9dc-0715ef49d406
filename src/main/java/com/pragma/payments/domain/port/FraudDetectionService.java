package com.pragma.payments.domain.port;

import com.pragma.payments.domain.model.Transaction;
import reactor.core.publisher.Mono;

/**
 * Puerto para el servicio de detección de fraude.
 * Define las operaciones que el dominio necesita para interactuar
 * con servicios externos de detección de fraude.
 */
public interface FraudDetectionService {
    /**
     * Evalúa una transacción para detectar posibles fraudes.
     * @param transaction La transacción a evaluar
     * @return Mono con el resultado de la evaluación de fraude
     */
    Mono<String> evaluateForFraud(Transaction transaction);

    /**
     * Obtiene el estado actual de un servicio de detección de fraude.
     * @return Mono con el estado del servicio (ej: "UP", "DOWN", "DEGRADED")
     */
    Mono<String> getServiceStatus();
}