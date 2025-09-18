package models;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class Account {
    private final String accountId;
    private final UUID ownerUserId;
    private BigDecimal balance;
    private final Instant createdAt;
    private boolean active;

    public Account(UUID ownerUserId){
        this.accountId = UUID.randomUUID().toString();
        this.ownerUserId = ownerUserId;
        this.balance = BigDecimal.ZERO.setScale(2);
        this.createdAt = Instant.now();
        this.active = true;
    }

    public  String getAccountId(){
        return accountId;
    }

    public UUID getOwnerUserId(){
        return ownerUserId;
    }

    public BigDecimal getBalance(){
        return balance;
    }

    public void setBalance(BigDecimal balance){
        this.balance = balance.setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }

    public Instant getCreatedAt(){
        return createdAt;
    }

    public boolean isActive(){
        return active;
    }

    public void deactivate(){
        this.active = false;
    }

}
