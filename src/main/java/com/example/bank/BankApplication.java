package com.example.bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
public class BankApplication {

    @GetMapping("/ping2")
    public String ping2() {
        return "OK2";
    }

    public static void main(String[] args) {
        SpringApplication.run(BankApplication.class, args);
    }
}

