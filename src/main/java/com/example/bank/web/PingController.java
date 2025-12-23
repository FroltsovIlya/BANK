package com.example.bank.web;

import org.springframework.web.bind.annotation.*;

@RestController
public class PingController {

    @GetMapping("/ping")
    public String ping() {
        return "OK";
    }
}