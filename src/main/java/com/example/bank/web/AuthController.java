package com.example.bank.web;

import com.example.bank.auth.AuthService;
import com.example.bank.user.User;
import com.example.bank.web.dto.LoginRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public User login(@RequestBody LoginRequest request) {
        return authService.login(
                request.getUsername(),
                request.getPassword()
        );
    }
}
