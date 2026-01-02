package com.adventure.auth.service;

import com.adventure.auth.dto.LoginRequest;
import com.adventure.auth.model.User;
import com.adventure.auth.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    @Test
    void shouldLoginWithValidCredentials() {
        // arrange
        LoginRequest request = new LoginRequest();
        request.setEmail("test@email.com");
        request.setPassword("123456");

        User user = new User();
        user.setEmail("test@email.com");
        user.setPassword("hashed-password");

        when(userRepository.findByEmail("test@email.com"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches("123456", "hashed-password"))
                .thenReturn(true);

        when(jwtService.generateToken("test@email.com"))
                .thenReturn("fake-jwt-token");

        // act
        String token = authService.login(request);

        // assert
        assertEquals("fake-jwt-token", token);
    }
}
