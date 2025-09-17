package repositories.memoires;

import models.User;
import repositories.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class InMemoryUserRepository implements UserRepository {
    private final Map<UUID, User> users = new HashMap<>();

    @Override
    public Optional<User> findByEmail(String email) {
        return users.values().stream().filter(user -> user.getEmail().equals(email)).findFirst();
    }
    @Override
    public Optional<User> findById(UUID id) {
        return Optional.ofNullable(users.get(id));
    }
    @Override
    public boolean existsByEmail(String email) {
        return users.values().stream().anyMatch(user -> user.getEmail().equals(email));
    }
    @Override
    public void save(User user) {
        users.put(user.getId(), user);
    }



}
