package com.letsplay.services;

import com.letsplay.dtos.AuthResponse;
import com.letsplay.dtos.LoginRequest;
import com.letsplay.dtos.RegisterRequest;
import com.letsplay.entities.User;
import com.letsplay.repositories.UserRepository;
import com.letsplay.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // Kat-injecter UserRepository, PasswordEncoder, w JwtService automatic b Lombok
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    // 🚀 1. LOGIQUE DYAL REGISTER (Inscription)
    public AuthResponse register(RegisterRequest request) {
        // Tchekiw wach l'email déjà mkhdem f la base de données
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Had l'email déjà m3mri bih un compte!");
        }

        // Créer l'objet User w hachi l'password b BCrypt direct mn l'Bean li configurna f SecurityConfig
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword())) // Hachage + Salting automatic
                .role(request.getRole().toUpperCase()) // Dima upper case bch tmatchi m3a Spring Security (USER / ADMIN)
                .build();

        // Sauvegarder f la base de données
        User savedUser = userRepository.save(user);

        // Générer l'token jdid b l'Id dyalo li tcreea automatic f MongoDB
        String token = jwtService.generateToken(savedUser.getId(), savedUser.getEmail(), savedUser.getRole());

        return AuthResponse.builder()
                .token(token)
                .email(savedUser.getEmail())
                .role(savedUser.getRole())
                .build();
    }

    // 🔐 2. LOGIQUE DYAL LOGIN (Connexion)
    public AuthResponse login(LoginRequest request) {
        // 9leb 3la l'user b l'email, ila mal9itoch l70eh error
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("L'email aw l'password ghaltin!"));

        // Tcheki wach password li dkhel l'user matchi m3a dak l'password li mshfer f la base
        // ⚠️ Mat-stkhdemch equals() bmarra, darouri matches() dyal BCrypt!
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("L'email aw l'password ghaltin!");
        }

        // Générer l'token jdid hit kolchi factory safe
        String token = jwtService.generateToken(user.getId(), user.getEmail(), user.getRole());

        return AuthResponse.builder()
                .token(token)
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}
