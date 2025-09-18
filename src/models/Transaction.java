package models;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class Transaction {
    private final UUID id;
    private final Instant timestamp;
    private final String accountId;
    private final transactionType type;
    private final BigDecimal amount;
    private final String counterpartyAccountId;
    private final String description;

    public enum transactionType {
        DEPOSIT, WITHDRAW, TRANSFERIN, TRANSFEROUT
    }

    public Transaction(String accountId, transactionType type, BigDecimal amount, String counterpartyAccountId, String description) {
        this.id = UUID.randomUUID();;
        this.timestamp = Instant.now();
        this.accountId = accountId;
        this.type = type;
        this.amount = amount.setScale(2, BigDecimal.ROUND_HALF_UP);;
        this.counterpartyAccountId = counterpartyAccountId;
        this.description = description;
    }

    public UUID getId(){
        return this.id;
    }

    public Instant getTimestamp(){
        return this.timestamp;
    }

    public String getAccountId(){
        return this.accountId;
    }

    public transactionType getType(){
        return this.type;
    }

    public BigDecimal getAmount(){
        return this.amount;
    }

    public String getCounterpartyAccountId(){
        return this.counterpartyAccountId;
    }

    public String getDescription(){
        return this.description;
    }

    public String toString() {
        return "Transaction{" +
                "id=" + this.id +
                ", timestamp=" + this.timestamp +
                ", accountId='" + this.accountId + '\'' +
                ", type=" + this.type +
                ", amount=" + this.amount +
                (this.counterpartyAccountId != null ? ", counterpartyAccountId='" + this.counterpartyAccountId + '\'' : "") +
                (this.description != null ? ", description='" + this.description + '\'' : "") +
                '}';
    }

}
