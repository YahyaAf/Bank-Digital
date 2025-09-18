package repositories.memoires;

import models.Transaction;
import repositories.TransactionRepository;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryTransactionRepository implements TransactionRepository {
    private final Map<UUID, Transaction> transactions = new HashMap<>();

    public void save(Transaction transaction){
        transactions.put(transaction.getId(), transaction);
    }

    public Optional<Transaction> findById(UUID id){
        return Optional.ofNullable(transactions.get(id));
    }

    public List<Transaction> findByAccountId(String accountId){
        return transactions.values().stream().filter(tx -> tx.getAccountId().equals(accountId))
                .sorted(Comparator.comparing(Transaction::getTimestamp)).collect(Collectors.toList());
    }

}
