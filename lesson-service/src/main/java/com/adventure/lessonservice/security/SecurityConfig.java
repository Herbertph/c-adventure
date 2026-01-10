package com.adventure.lessonservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // PUBLIC
                .requestMatchers("/lessons/**").permitAll()

                // PROGRESS (usa SecurityUtils, não JWT aqui)
                .requestMatchers("/progress/**").authenticated()

                // ADMIN protegido por X-Admin-Secret
                .anyRequest().permitAll()
            );

        return http.build();
    }
}
