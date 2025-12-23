package com.example.bank.repository;

import com.example.bank.user.User;

public interface UserRepository {
    User findByUsername(String username);
}
