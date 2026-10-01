package com.pragma.payments.application.service;

import com.pragma.payments.domain.model.Transaction;
import com.pragma.payments.domain.port.CoreBankingService;
import com.pragma.payments.domain.port.FraudDetectionService;
import com.pragma.payments.domain.port.RiskAssessmentService;
import com.pragma.payments.domain.port.TransactionRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Servicio de aplicación que orquesta el flujo de transacciones reactivas.
 * Coordina la interacción entre el detector de fraude, el buró de riesgos,
 * el core bancario y el repositorio de transacciones.
 */
@Service
public class TransactionService {

    private static final Logger log = LoggerFactory.getLogger(TransactionService.class);
    private static final String APPROVED = "APPROVED";
    private static final String REJECTED = "REJECTED";
    private static final String PROCESSED = "PROCESSED";
    private static final String FAILED = "FAILED";

    private final TransactionRepository transactionRepository;
    private final FraudDetectionService fraudDetectionService;
    private final RiskAssessmentService riskAssessmentService;
    private final CoreBankingService coreBankingService;

    public TransactionService(
            TransactionRepository transactionRepository,
            FraudDetectionService fraudDetectionService,
            RiskAssessmentService riskAssessmentService,
            CoreBankingService coreBankingService) {
        this.transactionRepository = transactionRepository;
        this.fraudDetectionService = fraudDetectionService;
        this.riskAssessmentService = riskAssessmentService;
        this.coreBankingService = coreBankingService;
    }

    /**
     * Crea y procesa una nueva transacción aplicando el flujo reactivo completo.
     * @param accountOrigin Cuenta origen
     * @param accountDestination Cuenta destino
     * @param amount Monto de la transacción
     * @return Mono que emite la transacción procesada
     */
    public Mono<Transaction> createAndProcessTransaction(
            String accountOrigin,
            String accountDestination,
            BigDecimal amount) {

        log.info("Iniciando procesamiento de transacción: origen={}, destino={}, monto={}",
                accountOrigin, accountDestination, amount);

        Transaction transaction = Transaction.create(
                accountOrigin,
                accountDestination,
                amount
        );

        return transactionRepository.save(transaction)
                .flatMap(this::executeFraudDetection)
                .flatMap(this::executeRiskAssessment)
                .flatMap(this::executeCoreBankingProcessing)
                .flatMap(this::finalizeTransaction)
                .doOnSuccess(t -> log.info("Transacción procesada exitosamente: id={}, estado={}",
                        t.id(), t.status()))
                .doOnError(e -> log.error("Error en procesamiento de transacción: {}", e.getMessage()));
    }

    /**
     * Ejecuta la detección de fraude sobre la transacción.
     * @param transaction Transacción a evaluar
     * @return Mono con la transacción actualizada
     */
    @CircuitBreaker(name = "fraudDetection", fallbackMethod = "fraudDetectionFallback")
    @Retry(name = "fraudDetectionRetry")
    private Mono<Transaction> executeFraudDetection(Transaction transaction) {
        log.debug("Ejecutando detección de fraude para transacción: {}", transaction.id());

        return fraudDetectionService.evaluateForFraud(transaction)
                .map(result -> {
                    Transaction updated = transaction.withFraudDetectionResult(result);
                    log.info("Resultado detección de fraude para {}: {}", transaction.id(), result);
                    return updated;
                });
    }

    /**
     * Ejecuta la evaluación de riesgos sobre la transacción.
     * @param transaction Transacción a evaluar
     * @return Mono con la transacción actualizada
     */
    @CircuitBreaker(name = "riskAssessment", fallbackMethod = "riskAssessmentFallback")
    @Retry(name = "riskAssessmentRetry")
    private Mono<Transaction> executeRiskAssessment(Transaction transaction) {
        log.debug("Ejecutando evaluación de riesgo para transacción: {}", transaction.id());

        if (REJECTED.equals(transaction.fraudDetectionResult())) {
            log.info("Transacción rechazada por fraude, saltando evaluación de riesgo: {}", transaction.id());
            return Mono.just(transaction);
        }

        return riskAssessmentService.evaluateRisk(transaction)
                .map(result -> {
                    Transaction updated = transaction.withRiskAssessmentResult(result);
                    log.info("Resultado evaluación de riesgo para {}: {}", transaction.id(), result);
                    return updated;
                });
    }

    /**
     * Procesa la transacción en el core bancario.
     * @param transaction Transacción a procesar
     * @return Mono con la transacción actualizada
     */
    @CircuitBreaker(name = "coreBanking", fallbackMethod = "coreBankingFallback")
    @Retry(name = "coreBankingRetry")
    private Mono<Transaction> executeCoreBankingProcessing(Transaction transaction) {
        log.debug("Ejecutando procesamiento en core bancario para transacción: {}", transaction.id());

        if (REJECTED.equals(transaction.fraudDetectionResult()) ||
            REJECTED.equals(transaction.riskAssessmentResult())) {
            log.info("Transacción rechazada en validación previa, no se procesa en core: {}", transaction.id());
            return Mono.just(transaction.markAsFailed());
        }

        return coreBankingService.processTransaction(transaction)
                .map(result -> {
                    Transaction updated = transaction.withCoreBankingResult(result);
                    log.info("Resultado procesamiento core bancario para {}: {}", transaction.id(), result);
                    return updated;
                });
    }

    /**
     * Finaliza la transacción guardando el resultado en el repositorio.
     * @param transaction Transacción a finalizar
     * @return Mono con la transacción finalizada
     */
    private Mono<Transaction> finalizeTransaction(Transaction transaction) {
        log.debug("Finalizando transacción: {}", transaction.id());

        boolean isSuccess = APPROVED.equals(transaction.coreBankingResult());
        Transaction finalTransaction = isSuccess ?
                transaction.markAsCompleted() :
                transaction.markAsFailed();

        return transactionRepository.save(finalTransaction)
                .doOnSuccess(t -> log.info("Transacción finalizada y guardada: id={}, estado={}",
                        t.id(), t.status()));
    }

    /**
     * Consulta transacciones por cuenta origen.
     * @param accountOrigin Cuenta origen
     * @return Flux de transacciones
     */
    public Flux<Transaction> findByAccountOrigin(String accountOrigin) {
        log.debug("Consultando transacciones por cuenta origen: {}", accountOrigin);
        return transactionRepository.findByAccountOrigin(accountOrigin);
    }

    /**
     * Consulta transacciones por cuenta destino.
     * @param accountDestination Cuenta destino
     * @return Flux de transacciones
     */
    public Flux<Transaction> findByAccountDestination(String accountDestination) {
        log.debug("Consultando transacciones por cuenta destino: {}", accountDestination);
        return transactionRepository.findByAccountDestination(accountDestination);
    }

    /**
     * Consulta transacciones por rango de fechas.
     * @param startDate Fecha inicial
     * @param endDate Fecha final
     * @return Flux de transacciones
     */
    public Flux<Transaction> findByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        log.debug("Consultando transacciones entre {} y {}", startDate, endDate);
        return transactionRepository.findByCreatedAtBetween(startDate, endDate);
    }

    /**
     * Consulta una transacción por ID.
     * @param id ID de la transacción
     * @return Mono con la transacción
     */
    public Mono<Transaction> findById(UUID id) {
        log.debug("Consultando transacción por ID: {}", id);
        return transactionRepository.findById(id);
    }

    /**
     * Procesa múltiples transacciones en paralelo usando merge.
     * @param transactions Flux de transacciones a procesar
     * @return Flux de transacciones procesadas
     */
    public Flux<Transaction> processMultipleTransactions(Flux<Transaction> transactions) {
        log.info("Procesando múltiples transacciones en paralelo");

        return transactions
                .flatMap(this::executeFraudDetection)
                .flatMap(this::executeRiskAssessment)
                .flatMap(this::executeCoreBankingProcessing)
                .flatMap(this::finalizeTransaction);
    }

    /**
     * Procesa transacciones con verificación de crédito previa.
     * @param accountOrigin Cuenta origen
     * @param accountDestination Cuenta destino
     * @param amount Monto
     * @return Mono de transacción procesada
     */
    public Mono<Transaction> processTransactionWithCreditCheck(
            String accountOrigin,
            String accountDestination,
            BigDecimal amount) {

        return riskAssessmentService.checkCreditLimit(accountOrigin, amount)
                .filter(hasLimit -> hasLimit)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Límite de crédito insuficiente")))
                .flatMap(hasLimit -> createAndProcessTransaction(accountOrigin, accountDestination, amount));
    }

    /**
     * Ejecuta múltiples validaciones en paralelo y combina los resultados.
     * @param transaction Transacción a validar
     * @return Tuple2 con resultados de fraude y riesgo
     */
    private Mono<Tuple2<String, String>> executeParallelValidations(Transaction transaction) {
        log.debug("Ejecutando validaciones en paralelo para: {}", transaction.id());

        Mono<String> fraudCheck = fraudDetectionService.evaluateForFraud(transaction)
                .doOnNext(result -> log.info("Fraude evaluado: {}", result));

        Mono<String> riskCheck = riskAssessmentService.evaluateRisk(transaction)
                .doOnNext(result -> log.info("Riesgo evaluado: {}", result));

        return Mono.zip(fraudCheck, riskCheck);
    }

    /**
     * Fallback para detección de fraude cuando el circuito está abierto.
     */
    private Mono<Transaction> fraudDetectionFallback(Transaction transaction, Throwable ex) {
        log.warn("Fallback de detección de fraude activado para {}: {}", transaction.id(), ex.getMessage());
        return Mono.just(transaction.withFraudDetectionResult(APPROVED));
    }

    /**
     * Fallback para evaluación de riesgos cuando el circuito está abierto.
     */
    private Mono<Transaction> riskAssessmentFallback(Transaction transaction, Throwable ex) {
        log.warn("Fallback de evaluación de riesgos activado para {}: {}", transaction.id(), ex.getMessage());
        return Mono.just(transaction.withRiskAssessmentResult(APPROVED));
    }

    /**
     * Fallback para procesamiento del core bancario cuando el circuito está abierto.
     */
    private Mono<Transaction> coreBankingFallback(Transaction transaction, Throwable ex) {
        log.warn("Fallback de core bancario activado para {}: {}", transaction.id(), ex.getMessage());
        return Mono.just(transaction.withCoreBankingResult(REJECTED));
    }
}