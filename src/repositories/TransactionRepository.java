package repositories;

import models.Transaction;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionRepository {
    void save(Transaction transaction);
    Optional<Transaction> findById(UUID id);
    List<Transaction> findByAccountId(String accountId);

}
