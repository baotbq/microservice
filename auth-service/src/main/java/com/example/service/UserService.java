package com.example.service;

import com.example.model.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    // Hardcoded users - no database
    private final Map<String, User> users = new HashMap<>();

    public UserService() {
        // Initialize with some test users
        users.put("admin", new User("admin", "admin123", "ADMIN", "admin@example.com"));
        users.put("user", new User("user", "user123", "USER", "user@example.com"));
        users.put("moderator", new User("moderator", "mod123", "MODERATOR", "mod@example.com"));
    }

    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(users.get(username));
    }

    public boolean validateCredentials(String username, String password) {
        User user = users.get(username);
        return user != null && user.getPassword().equals(password);
    }
}
