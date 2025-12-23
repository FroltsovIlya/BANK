package com.example.bank.repository.impl;

import com.example.bank.repository.UserRepository;
import com.example.bank.user.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InMemoryUserRepository implements UserRepository {

    private final List<User> users = List.of(
            new User("Ilya", "passwd", false),
            new User("Vasya", "passwd3", false),
            new User("Admin", "Admin", true)
    );

    @Override
    public User findByUsername(String username) {
        return users.stream()
                .filter(u -> u.getUserName().equals(username))
                .findFirst()
                .orElse(null);
    }
}
