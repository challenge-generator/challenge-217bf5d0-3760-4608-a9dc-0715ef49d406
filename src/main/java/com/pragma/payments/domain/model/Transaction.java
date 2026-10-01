package com.pragma.payments.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entidad que representa una transacción financiera en el sistema.
 * Contiene todos los atributos necesarios para procesar y validar una transacción,
 * incluyendo información del originador, destinatario y montos involucrados.
 */
public record Transaction(
    UUID id,
    String accountOrigin,
    String accountDestination,
    BigDecimal amount,
    TransactionStatus status,
    String reference,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    String currency,
    String description,
    String paymentMethod,
    String fraudDetectionResult,
    String riskAssessmentResult,
    String coreBankingResult
) {
    /**
     * Constructor que inicializa una transacción con los campos básicos requeridos.
     * Los campos de estado y timestamps se inicializan con valores por defecto.
     */
    public Transaction {
        if (id == null) {
            id = UUID.randomUUID();
        }
        if (accountOrigin == null || accountOrigin.isBlank()) {
            throw new IllegalArgumentException("La cuenta de origen no puede ser nula o vacía");
        }
        if (accountDestination == null || accountDestination.isBlank()) {
            throw new IllegalArgumentException("La cuenta de destino no puede ser nula o vacía");
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser positivo");
        }
        if (currency == null || currency.isBlank()) {
            throw new IllegalArgumentException("La moneda no puede ser nula o vacía");
        }
        if (status == null) {
            status = TransactionStatus.PENDING;
        }
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (updatedAt == null) {
            updatedAt = createdAt;
        }
    }

    /**
     * Crea una nueva transacción con estado PENDING y timestamps actualizados.
     */
    public static Transaction create(
            String accountOrigin,
            String accountDestination,
            BigDecimal amount,
            String reference,
            String currency,
            String description,
            String paymentMethod
    ) {
        return new Transaction(
                UUID.randomUUID(),
                accountOrigin,
                accountDestination,
                amount,
                TransactionStatus.PENDING,
                reference,
                LocalDateTime.now(),
                LocalDateTime.now(),
                currency,
                description,
                paymentMethod,
                null,
                null,
                null
        );
    }

    /**
     * Actualiza el estado de la transacción y registra el resultado de la detección de fraude.
     */
    public Transaction withFraudDetectionResult(String fraudResult) {
        return new Transaction(
                this.id,
                this.accountOrigin,
                this.accountDestination,
                this.amount,
                this.status,
                this.reference,
                this.createdAt,
                LocalDateTime.now(),
                this.currency,
                this.description,
                this.paymentMethod,
                fraudResult,
                this.riskAssessmentResult,
                this.coreBankingResult
        );
    }

    /**
     * Actualiza el estado de la transacción y registra el resultado de la evaluación de riesgo.
     */
    public Transaction withRiskAssessmentResult(String riskResult) {
        return new Transaction(
                this.id,
                this.accountOrigin,
                this.accountDestination,
                this.amount,
                this.status,
                this.reference,
                this.createdAt,
                LocalDateTime.now(),
                this.currency,
                this.description,
                this.paymentMethod,
                this.fraudDetectionResult,
                riskResult,
                this.coreBankingResult
        );
    }

    /**
     * Actualiza el estado de la transacción y registra el resultado del core bancario.
     */
    public Transaction withCoreBankingResult(String coreResult) {
        return new Transaction(
                this.id,
                this.accountOrigin,
                this.accountDestination,
                this.amount,
                this.status,
                this.reference,
                this.createdAt,
                LocalDateTime.now(),
                this.currency,
                this.description,
                this.paymentMethod,
                this.fraudDetectionResult,
                this.riskAssessmentResult,
                coreResult
        );
    }

    /**
     * Actualiza el estado de la transacción a COMPLETED.
     */
    public Transaction markAsCompleted() {
        return new Transaction(
                this.id,
                this.accountOrigin,
                this.accountDestination,
                this.amount,
                TransactionStatus.COMPLETED,
                this.reference,
                this.createdAt,
                LocalDateTime.now(),
                this.currency,
                this.description,
                this.paymentMethod,
                this.fraudDetectionResult,
                this.riskAssessmentResult,
                this.coreBankingResult
        );
    }

    /**
     * Actualiza el estado de la transacción a FAILED.
     */
    public Transaction markAsFailed() {
        return new Transaction(
                this.id,
                this.accountOrigin,
                this.accountDestination,
                this.amount,
                TransactionStatus.FAILED,
                this.reference,
                this.createdAt,
                LocalDateTime.now(),
                this.currency,
                this.description,
                this.paymentMethod,
                this.fraudDetectionResult,
                this.riskAssessmentResult,
                this.coreBankingResult
        );
    }
}

/**
 * Enum que representa los posibles estados de una transacción.
 */
enum TransactionStatus {
    PENDING,
    IN_PROGRESS,
    COMPLETED,
    FAILED,
    REVERSED
}