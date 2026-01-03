package com.adventure.auth.controller;

import com.adventure.auth.dto.LoginRequest;
import com.adventure.auth.dto.RegisterRequest;
import com.adventure.auth.dto.UserResponse;
import com.adventure.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequest request) {
        String token = authService.login(request);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/me")
public ResponseEntity<UserResponse> me(
        @RequestHeader(value = "Authorization", required = false) String token
) {
    if (token == null || !token.startsWith("Bearer ")) {
        return ResponseEntity.status(401).build();
    }

    try {
        String cleanedToken = token.replace("Bearer ", "").trim();
        return ResponseEntity.ok(authService.getUserFromToken(cleanedToken));
    } catch (Exception e) {
        return ResponseEntity.status(401).build();
    }
}

@GetMapping("/health")
public String health() {
    return "OK";
}


}

