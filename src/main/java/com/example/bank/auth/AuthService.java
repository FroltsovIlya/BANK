package com.example.bank.auth;

import com.example.bank.repository.UserRepository;
import com.example.bank.user.User;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(String username, String password) {
        User user = userRepository.findByUsername(username);

        if (user == null || !user.checkPassword(password)) {
            throw new RuntimeException("Invalid credentials");
        }

        return user;
    }
}
