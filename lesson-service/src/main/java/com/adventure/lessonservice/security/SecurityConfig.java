package com.adventure.lessonservice.security;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

@Configuration
public class SecurityConfig {

    private static final String JWT_SECRET = System.getenv("JWT_SECRET");

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/lessons/**").permitAll()
                .requestMatchers("/progress/**").authenticated()
                .anyRequest().denyAll()
            )
            .oauth2ResourceServer(oauth -> oauth.jwt());

        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        var key = new SecretKeySpec(
            JWT_SECRET.getBytes(),
            "HmacSHA256"
        );
        return NimbusJwtDecoder.withSecretKey(key).build();
    }
}
