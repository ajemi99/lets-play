package com.letsplay.controllers;

import com.letsplay.dtos.AuthResponse;
import com.letsplay.dtos.LoginRequest;
import com.letsplay.dtos.RegisterRequest;
import com.letsplay.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth") // Dak l'lien li khllinah open f SecurityConfig
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // 📝 EndPoint dyal l'Inscription: POST http://localhost:8080/api/auth/register
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);
        return ResponseEntity.ok(response);
    }

    // 🔐 EndPoint dyal l'Connexion: POST http://localhost:8080/api/auth/login
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}