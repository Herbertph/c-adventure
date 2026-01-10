package com.adventure.lessonservice.security;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
            // PUBLIC
            .requestMatchers("/lessons/**").permitAll()

            // ADMIN (protegido por AdminGuard, NÃO JWT)
            .requestMatchers(
                HttpMethod.POST, "/lessons"
            ).permitAll()
            .requestMatchers(
                HttpMethod.PUT, "/lessons/**"
            ).permitAll()
            .requestMatchers(
                HttpMethod.DELETE, "/lessons/**"
            ).permitAll()

            // USER AUTH
            .requestMatchers("/progress/**").authenticated()

            .anyRequest().permitAll()
        )
        .oauth2ResourceServer(oauth -> oauth.jwt());

    return http.build();
}
}

