package com.pragma.payments.application.service;

import com.pragma.payments.domain.model.Transaction;
import com.pragma.payments.domain.model.TransactionStatus;
import com.pragma.payments.domain.port.FraudDetectionService;
import com.pragma.payments.domain.port.TransactionRepository;
import com.pragma.payments.infrastructure.adapter.CoreBankingAdapter;
import com.pragma.payments.infrastructure.adapter.PaymentGatewayAdapter;
import com.pragma.payments.infrastructure.adapter.RiskAssessmentAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests para TransactionService - Paradigma Reactivo")
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private FraudDetectionService fraudDetectionService;

    @Mock
    private RiskAssessmentAdapter riskAssessmentAdapter;

    @Mock
    private CoreBankingAdapter coreBankingAdapter;

    @Mock
    private PaymentGatewayAdapter paymentGatewayAdapter;

    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        transactionService = new TransactionService(
            transactionRepository,
            fraudDetectionService,
            riskAssessmentAdapter,
            coreBankingAdapter,
            paymentGatewayAdapter
        );
    }

    @Test
    @DisplayName("Debe crear transacción exitosamente con todos los servicios de validación")
    void shouldCreateTransactionSuccessfully() {
        Transaction transaction = Transaction.create(
            "ACC-001",
            "ACC-002",
            new BigDecimal("1000.00"),
            "Pago de servicio"
        );

        when(transactionRepository.save(any(Transaction.class)))
            .thenReturn(Mono.just(transaction.withFraudDetectionResult("APPROVED")));
        when(fraudDetectionService.evaluateForFraud(any(Transaction.class)))
            .thenReturn(Mono.just("APPROVED"));
        when(riskAssessmentAdapter.evaluateRisk(any(Transaction.class)))
            .thenReturn(Mono.just("LOW_RISK"));
        when(coreBankingAdapter.processTransaction(any(Transaction.class)))
            .thenReturn(Mono.just("PROCESSED"));
        when(paymentGatewayAdapter.executePayment(any(Transaction.class)))
            .thenReturn(Mono.just("SUCCESS"));

        StepVerifier.create(transactionService.createTransaction(transaction))
            .assertNext(result -> {
                assertThat(result).isNotNull();
                assertThat(result.fraudDetectionResult()).isEqualTo("APPROVED");
            })
            .verifyComplete();

        verify(fraudDetectionService).evaluateForFraud(any(Transaction.class));
        verify(riskAssessmentAdapter).evaluateRisk(any(Transaction.class));
        verify(coreBankingAdapter).processTransaction(any(Transaction.class));
    }

    @Test
    @DisplayName("Debe rechazar transacción cuando el servicio de fraude retorna FRAUD_DETECTED")
    void shouldRejectTransactionWhenFraudDetected() {
        Transaction transaction = Transaction.create(
            "ACC-001",
            "ACC-002",
            new BigDecimal("5000.00"),
            "Transferencia sospechosa"
        );

        when(fraudDetectionService.evaluateForFraud(any(Transaction.class)))
            .thenReturn(Mono.just("FRAUD_DETECTED"));
        when(transactionRepository.save(any(Transaction.class)))
            .thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        StepVerifier.create(transactionService.createTransaction(transaction))
            .expectErrorMatches(throwable -> 
                throwable.getMessage().contains("Transacción rechazada por fraude") ||
                throwable.getMessage().contains("FRAUD")
            )
            .verify();
    }

    @Test
    @DisplayName("Debe manejar error al comunicarse con el servicio de fraude")
    void shouldHandleFraudServiceError() {
        Transaction transaction = Transaction.create(
            "ACC-001",
            "ACC-002",
            new BigDecimal("1000.00"),
            "Pago de servicio"
        );

        when(fraudDetectionService.evaluateForFraud(any(Transaction.class)))
            .thenReturn(Mono.error(new RuntimeError("Servicio de fraude no disponible"))));
        when(transactionRepository.save(any(Transaction.class)))
            .thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        StepVerifier.create(transactionService.createTransaction(transaction))
            .expectError()
            .verify();
    }

    @Test
    @DisplayName("Debe obtener transacción por ID exitosamente")
    void shouldGetTransactionById() {
        UUID transactionId = UUID.randomUUID();
        Transaction transaction = Transaction.create(
            "ACC-001",
            "ACC-002",
            new BigDecimal("1000.00"),
            "Pago de servicio"
        );

        when(transactionRepository.findById(transactionId))
            .thenReturn(Mono.just(transaction));

        StepVerifier.create(transactionService.getTransactionById(transactionId))
            .assertNext(result -> {
                assertThat(result).isNotNull();
                assertThat(result.accountOrigin()).isEqualTo("ACC-001");
            })
            .verifyComplete();
    }

    @Test
    @DisplayName("Debe retornar Mono.empty cuando la transacción no existe")
    void shouldReturnEmptyWhenTransactionNotFound() {
        UUID transactionId = UUID.randomUUID();

        when(transactionRepository.findById(transactionId))
            .thenReturn(Mono.empty());

        StepVerifier.create(transactionService.getTransactionById(transactionId))
            .verifyComplete();
    }

    @Test
    @DisplayName("Debe obtener transacciones por cuenta de origen")
    void shouldGetTransactionsByAccountOrigin() {
        String accountOrigin = "ACC-001";
        Transaction t1 = Transaction.create(accountOrigin, "ACC-002", new BigDecimal("100.00"), "Pago 1");
        Transaction t2 = Transaction.create(accountOrigin, "ACC-003", new BigDecimal("200.00"), "Pago 2");

        when(transactionRepository.findByAccountOrigin(accountOrigin))
            .thenReturn(Flux.just(t1, t2));

        StepVerifier.create(transactionService.getTransactionsByAccountOrigin(accountOrigin))
            .expectNextCount(2)
            .verifyComplete();
    }

    @Test
    @DisplayName("Debe actualizar el estado de una transacción")
    void shouldUpdateTransactionStatus() {
        UUID transactionId = UUID.randomUUID();
        Transaction transaction = Transaction.create(
            "ACC-001",
            "ACC-002",
            new BigDecimal("1000.00"),
            "Pago de servicio"
        ).markAsCompleted();

        when(transactionRepository.updateStatus(transactionId, TransactionStatus.COMPLETED.name()))
            .thenReturn(Mono.just(transaction));

        StepVerifier.create(
            transactionService.updateTransactionStatus(transactionId, TransactionStatus.COMPLETED.name())
        )
            .assertNext(result -> {
                assertThat(result).isNotNull();
            })
            .verifyComplete();
    }

    @Test
    @DisplayName("Debe encadenar servicios de forma reactiva con flatMap")
    void shouldChainServicesReactively() {
        Transaction transaction = Transaction.create(
            "ACC-001",
            "ACC-002",
            new BigDecimal("500.00"),
            "Pago encadenado"
        );

        when(fraudDetectionService.evaluateForFraud(any(Transaction.class)))
            .thenReturn(Mono.just("APPROVED"));
        when(riskAssessmentAdapter.evaluateRisk(any(Transaction.class)))
            .thenReturn(Mono.just("LOW_RISK"));
        when(transactionRepository.save(any(Transaction.class)))
            .thenReturn(Mono.just(transaction));

        StepVerifier.create(
            transactionService.createTransaction(transaction)
                .flatMap(t -> transactionRepository.findById(t.id()))
        )
            .assertNext(result -> assertThat(result).isNotNull())
            .verifyComplete();
    }

    @Test
    @DisplayName("Debe aplicar manejo de errores con onErrorResume")
    void shouldApplyErrorHandlingWithOnErrorResume() {
        Transaction transaction = Transaction.create(
            "ACC-001",
            "ACC-002",
            new BigDecimal("1000.00"),
            "Pago con manejo de errores"
        );

        when(fraudDetectionService.evaluateForFraud(any(Transaction.class)))
            .thenReturn(Mono.error(new RuntimeException("Error temporal")));
        when(transactionRepository.save(any(Transaction.class)))
            .thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        StepVerifier.create(
            transactionService.createTransactionWithFallback(transaction)
        )
            .assertNext(result -> {
                assertThat(result).isNotNull();
            })
            .verifyComplete();
    }

    @Test
    @DisplayName("Debe filtrar transacciones con operador filter")
    void shouldFilterTransactionsWithOperator() {
        Transaction t1 = Transaction.create("ACC-001", "ACC-002", new BigDecimal("100.00"), "Pago bajo");
        Transaction t2 = Transaction.create("ACC-001", "ACC-003", new BigDecimal("5000.00"), "Pago alto");
        Transaction t3 = Transaction.create("ACC-001", "ACC-004", new BigDecimal("200.00"), "Pago bajo 2");

        when(transactionRepository.findByAccountOrigin("ACC-001"))
            .thenReturn(Flux.just(t1, t2, t3));

        StepVerifier.create(
            transactionService.getTransactionsByAccountOrigin("ACC-001")
                .filter(t -> t.amount().compareTo(new BigDecimal("1000.00")) < 0)
        )
            .expectNextCount(2)
            .verifyComplete();
    }

    @Test
    @DisplayName("Debe transformar transacciones con operador map")
    void shouldTransformTransactionsWithMap() {
        Transaction transaction = Transaction.create(
            "ACC-001",
            "ACC-002",
            new BigDecimal("1000.00"),
            "Pago original"
        );

        when(transactionRepository.findById(transaction.id()))
            .thenReturn(Mono.just(transaction));

        StepVerifier.create(
            transactionService.getTransactionById(transaction.id())
                .map(t -> "Transacción: " + t.id() + "Monto: " + t.amount())
        )
            .assertNext(result -> assertThat(result).contains("Transacción:"))
            .verifyComplete();
    }
}