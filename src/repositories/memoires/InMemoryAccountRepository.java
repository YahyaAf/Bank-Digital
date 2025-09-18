package repositories.memoires;

import models.Account;
import repositories.AccountRepository;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryAccountRepository implements AccountRepository {
    private final Map<String, Account> accounts = new HashMap<>();

    @Override
    public void save(Account account) {
        accounts.put(account.getAccountId(),account);
    }

    @Override
    public void delete(String accountId) {
        accounts.remove(accountId);
    }

    @Override
    public List<Account> findByUserId(UUID userId){
        return accounts.values().stream().filter(account -> account.getOwnerUserId().equals(userId)).collect(Collectors.toList());
    }

    @Override
    public boolean existsById(String accountId) {
        return accounts.containsKey(accountId);
    }

    @Override
    public Optional<Account> findById(String accountId) {
        return Optional.ofNullable(accounts.get(accountId));
    }
}
