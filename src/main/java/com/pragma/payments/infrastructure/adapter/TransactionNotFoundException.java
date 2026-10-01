package com.pragma.payments.infrastructure.adapter;


import com.pragma.payments.domain.model.Transaction;
public class TransactionNotFoundException extends RuntimeException {
    private final String transactionId;
    private final String accountOrigin;
    private final String accountDestination;
    private final java.time.LocalDateTime searchTimestamp;

    public TransactionNotFoundException(String transactionId) {
        super("Transaction not found with id: " + transactionId);
        this.transactionId = transactionId;
        this.accountOrigin = null;
        this.accountDestination = null;
        this.searchTimestamp = java.time.LocalDateTime.now();
    }

    public TransactionNotFoundException(String transactionId, Throwable cause) {
        super("Transaction not found with id: " + transactionId, cause);
        this.transactionId = transactionId;
        this.accountOrigin = null;
        this.accountDestination = null;
        this.searchTimestamp = java.time.LocalDateTime.now();
    }

    public TransactionNotFoundException forAccountOrigin(String accountOrigin) {
        this.accountOrigin = accountOrigin;
        return this;
    }

    public TransactionNotFoundException forAccountDestination(String accountDestination) {
        this.accountDestination = accountDestination;
        return this;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getAccountOrigin() {
        return accountOrigin;
    }

    public String getAccountDestination() {
        return accountDestination;
    }

    public java.time.LocalDateTime getSearchTimestamp() {
        return searchTimestamp;
    }

    public String getDetailedMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append("Transaction not found");
        if (transactionId != null) {
            sb.append(" [id=").append(transactionId).append("]");
        }
        if (accountOrigin != null) {
            sb.append(" [origin=").append(accountOrigin).append("]");
        }
        if (accountDestination != null) {
            sb.append(" [destination=").append(accountDestination).append("]");
        }
        return sb.toString();
    }
}