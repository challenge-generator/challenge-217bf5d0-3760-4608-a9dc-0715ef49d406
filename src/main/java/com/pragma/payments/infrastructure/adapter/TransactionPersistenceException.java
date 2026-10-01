package com.pragma.payments.infrastructure.adapter;


import com.pragma.payments.domain.model.Transaction;
public class TransactionPersistenceException extends RuntimeException {
    private final String operation;
    private final String transactionId;
    private final String originalMessage;

    public TransactionPersistenceException(String message) {
        super(message);
        this.operation = "UNKNOWN";
        this.transactionId = null;
        this.originalMessage = message;
    }

    public TransactionPersistenceException(String message, Throwable cause) {
        super(message, cause);
        this.operation = "UNKNOWN";
        this.transactionId = null;
        this.originalMessage = message;
    }

    public TransactionPersistenceException(String operation, String transactionId, String message, Throwable cause) {
        super(buildMessage(operation, transactionId, message), cause);
        this.operation = operation;
        this.transactionId = transactionId;
        this.originalMessage = message;
    }

    private static String buildMessage(String operation, String transactionId, String message) {
        StringBuilder sb = new StringBuilder("Transaction persistence error");
        sb.append(" [operation=").append(operation).append("]");
        if (transactionId != null) {
            sb.append(" [transactionId=").append(transactionId).append("]");
        }
        sb.append(": ").append(message);
        return sb.toString();
    }

    public String getOperation() {
        return operation;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getOriginalMessage() {
        return originalMessage;
    }

    public boolean isRetryable() {
        Throwable cause = getCause();
        if (cause instanceof org.springframework.dao.DataAccessException) {
            String className = cause.getClass().getSimpleName();
            return "DeadlockLoserDataAccessException".equals(className) ||
                   "CannotAcquireLockException".equals(className) ||
                   "TransientDataAccessException".equals(className);
        }
        return false;
    }
}