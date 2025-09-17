package repositories;

import models.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    Optional<User> findByEmail(String email);
    void save(User user);
    boolean existsByEmail(String email);
    Optional<User> findById(UUID id);
}
