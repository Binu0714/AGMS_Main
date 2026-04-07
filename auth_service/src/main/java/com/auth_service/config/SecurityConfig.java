package com.auth_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. Disable CSRF (Must do this for REST APIs to allow POST requests)
                .csrf(csrf -> csrf.disable())

                // 2. Define the "Open" and "Locked" doors
                .authorizeHttpRequests(auth -> auth
                        // Allow these paths without any login/token
                        // IMPORTANT: These must match your Controller @RequestMapping exactly
                        .requestMatchers("/auth/register", "/auth/login", "/auth/refresh").permitAll()

                        // Lock everything else
                        .anyRequest().authenticated()
                )

                // 3. Make the session stateless (Since we use JWT)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        return http.build();
    }
}