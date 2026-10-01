package com.pragma.payments.infrastructure.adapter;


import com.pragma.payments.domain.model.TransactionStatus;
import com.pragma.payments.domain.model.Transaction;
import com.pragma.payments.domain.port.TransactionRepository;
import io.r2dbc.postgresql.codec.Json;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public class TransactionRepositoryAdapter implements TransactionRepository {

    private final DatabaseClient databaseClient;

    public TransactionRepositoryAdapter(DatabaseClient databaseClient) {
        this.databaseClient = databaseClient;
    }

    @Override
    public Mono<Transaction> save(Transaction transaction) {
        String sql = """
            INSERT INTO transactions 
                (id, account_origin, account_destination, amount, currency, status, 
                 fraud_result, risk_result, core_result, created_at, updated_at)
            VALUES 
                (:id, :accountOrigin, :accountDestination, :amount, :currency, :status,
                 :fraudResult, :riskResult, :coreResult, :createdAt, :updatedAt)
            """;

        return databaseClient.sql(sql)
            .bind("id", transaction.id().toString())
            .bind("accountOrigin", transaction.accountOrigin())
            .bind("accountDestination", transaction.accountDestination())
            .bind("amount", transaction.amount().doubleValue())
            .bind("currency", transaction.currency())
            .bind("status", transaction.status().name())
            .bind("fraudResult", transaction.fraudResult() != null ? transaction.fraudResult() : Json.of("null"))
            .bind("riskResult", transaction.riskResult() != null ? transaction.riskResult() : Json.of("null"))
            .bind("coreResult", transaction.coreResult() != null ? transaction.coreResult() : Json.of("null"))
            .bind("createdAt", transaction.createdAt())
            .bind("updatedAt", transaction.updatedAt())
            .fetch()
            .rowsUpdated()
            .flatMap(rows -> {
                if (rows > 0) {
                    return Mono.just(transaction);
                }
                return Mono.error(new TransactionPersistenceException("SAVE", 
                    transaction.id().toString(), "Failed to save transaction, no rows affected"));
            })
            .onErrorResume(e -> Mono.error(new TransactionPersistenceException("SAVE", 
                transaction.id().toString(), "Database error during save", e)));
    }

    @Override
    public Mono<Transaction> findById(UUID id) {
        String sql = "SELECT * FROM transactions WHERE id = :id";
        
        return databaseClient.sql(sql)
            .bind("id", id.toString())
            .map((row, metadata) -> mapRowToTransaction(row))
            .first()
            .switchIfEmpty(Mono.error(new TransactionNotFoundException(id.toString())))
            .onErrorResume(TransactionNotFoundException.class, Mono::error)
            .onErrorResume(e -> Mono.error(new TransactionPersistenceException("FIND_BY_ID", 
                id.toString(), "Database error during findById", e)));
    }

    @Override
    public Flux<Transaction> findByAccountOrigin(String accountOrigin) {
        String sql = "SELECT * FROM transactions WHERE account_origin = :accountOrigin ORDER BY created_at DESC";
        
        return databaseClient.sql(sql)
            .bind("accountOrigin", accountOrigin)
            .map((row, metadata) -> mapRowToTransaction(row))
            .all()
            .onErrorResume(e -> Flux.error(new TransactionPersistenceException("FIND_BY_ORIGIN", 
                null, "Database error during findByAccountOrigin", e)));
    }

    @Override
    public Flux<Transaction> findByAccountDestination(String accountDestination) {
        String sql = "SELECT * FROM transactions WHERE account_destination = :accountDestination ORDER BY created_at DESC";
        
        return databaseClient.sql(sql)
            .bind("accountDestination", accountDestination)
            .map((row, metadata) -> mapRowToTransaction(row))
            .all()
            .onErrorResume(e -> Flux.error(new TransactionPersistenceException("FIND_BY_DESTINATION", 
                null, "Database error during findByAccountDestination", e)));
    }

    @Override
    public Mono<Transaction> updateStatus(UUID id, String status) {
        String sql = "UPDATE transactions SET status = :status, updated_at = :updatedAt WHERE id = :id";
        
        return databaseClient.sql(sql)
            .bind("id", id.toString())
            .bind("status", status)
            .bind("updatedAt", LocalDateTime.now())
            .fetch()
            .rowsUpdated()
            .flatMap(rows -> {
                if (rows > 0) {
                    return findById(id);
                }
                return Mono.error(new TransactionNotFoundException(id.toString()));
            })
            .onErrorResume(TransactionNotFoundException.class, Mono::error)
            .onErrorResume(e -> Mono.error(new TransactionPersistenceException("UPDATE_STATUS", 
                id.toString(), "Database error during updateStatus", e)));
    }

    @Override
    public Flux<Transaction> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate) {
        String sql = "SELECT * FROM transactions WHERE created_at BETWEEN :startDate AND :endDate ORDER BY created_at DESC";
        
        return databaseClient.sql(sql)
            .bind("startDate", startDate)
            .bind("endDate", endDate)
            .map((row, metadata) -> mapRowToTransaction(row))
            .all()
            .onErrorResume(e -> Flux.error(new TransactionPersistenceException("FIND_BY_DATE_RANGE", 
                null, "Database error during findByCreatedAtBetween", e)));
    }

    private Transaction mapRowToTransaction(org.springframework.r2dbc.core.Row row) {
        UUID id = UUID.fromString(row.get("id", String.class));
        String accountOrigin = row.get("account_origin", String.class);
        String accountDestination = row.get("account_destination", String.class);
        java.math.BigDecimal amount = row.get("amount", java.math.BigDecimal.class);
        String currency = row.get("currency", String.class);
        String statusStr = row.get("status", String.class);
        TransactionStatus status = TransactionStatus.valueOf(statusStr);
        String fraudResult = extractJsonValue(row.get("fraud_result", Json.class));
        String riskResult = extractJsonValue(row.get("risk_result", Json.class));
        String coreResult = extractJsonValue(row.get("core_result", Json.class));
        LocalDateTime createdAt = row.get("created_at", LocalDateTime.class);
        LocalDateTime updatedAt = row.get("updated_at", LocalDateTime.class);

        return new Transaction(id, accountOrigin, accountDestination, amount, currency, 
            status, fraudResult, riskResult, coreResult, createdAt, updatedAt);
    }

    private String extractJsonValue(Json json) {
        if (json == null || json.asString() == null) {
            return null;
        }
        String value = json.asString();
        if ("null".equals(value) || value.isBlank()) {
            return null;
        }
        return value;
    }
}