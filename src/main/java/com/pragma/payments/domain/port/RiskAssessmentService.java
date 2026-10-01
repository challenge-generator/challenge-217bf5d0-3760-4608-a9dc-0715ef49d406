package com.pragma.payments.domain.port;

import com.pragma.payments.domain.model.Transaction;
import reactor.core.publisher.Mono;

/**
 * Puerto para el servicio de evaluación de riesgos del buró de riesgos.
 * Define la interfaz que el dominio necesita para evaluar el riesgo de una transacción.
 */
public interface RiskAssessmentService {

    /**
     * Evalúa el riesgo de una transacción específica.
     * @param transaction La transacción a evaluar
     * @return Mono que emite el resultado de la evaluación de riesgo
     */
    Mono<String> evaluateRisk(Transaction transaction);

    /**
     * Obtiene el estado de disponibilidad del servicio de riesgo.
     * @return Mono que emite el estado del servicio
     */
    Mono<String> getServiceStatus();

    /**
     * Evalúa el límite de crédito disponible para una cuenta.
     * @param accountNumber Número de cuenta a verificar
     * @param amount Monto a verificar
     * @return Mono que emite true si el monto está dentro del límite
     */
    Mono<Boolean> checkCreditLimit(String accountNumber, java.math.BigDecimal amount);
}