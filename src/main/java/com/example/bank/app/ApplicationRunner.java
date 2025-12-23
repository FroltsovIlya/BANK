package com.example.bank.app;

import com.example.bank.auth.AuthService;
import com.example.bank.service.CardService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ApplicationRunner implements CommandLineRunner {

    private final AuthService authService;
    private final CardService cardService;

    public ApplicationRunner(AuthService authService, CardService cardService) {
        this.authService = authService;
        this.cardService = cardService;
    }

    @Override
    public void run(String... args) {
        System.out.println("Spring Bank application started");
    }
}
