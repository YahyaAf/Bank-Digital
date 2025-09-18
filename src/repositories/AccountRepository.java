package repositories;

import models.Account;
import models.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository {
    void save(Account account);
    Optional<Account> findById(String accountId);
    List<Account> findByUserId(UUID useId);
    void delete(String accountId);
    boolean existsById(String accountId);
}
