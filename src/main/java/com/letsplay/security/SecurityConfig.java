package com.letsplay.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // كتخلينا نقدرو نخدمو ب @PreAuthorize ف الـ Controllers من بعد
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. Disable CSRF حيت الـ API ديالنا Stateless وكتخدم ب JWT
            .csrf(AbstractHttpConfigurer::disable)
            
            // 2. Configuration ديال الـ Endpoints والـ Permissions
            .authorizeHttpRequests(auth -> auth
                // الـ Endpoints ديال l'authentification (Login/Register) مفتوحين للجميع
                .requestMatchers("/api/auth/**").permitAll()
                
                // الـ GET ديال les produits مسموح لأي واحد يشوفهم (Public access)
                .requestMatchers(HttpMethod.GET, "/products/**").permitAll()
                
                // الـ Endpoints ديال الـ Users مخصصة فقط للـ ADMIN
                .requestMatchers("/users/**").hasRole("ADMIN")
                
                // أي requête أخرى خاصها تكون ضروري Authentifiée
                .anyRequest().authenticated()
            )
            
            // 3. خليه Stateless (ما يعقلش على الـ Session ف السيرفر)
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            
            // 4. دوز الـ Filter ديال JWT هو الأول قبل ما يدوز الـ Filter الافتراضي ديال Spring
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // 5. الـ Bean ديال تشفير الـ Passwords بـ BCrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}