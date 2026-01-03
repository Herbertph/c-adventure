package com.adventure.lessonservice.security;

import com.adventure.lessonservice.exception.AdminAccessDeniedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AdminGuard {

    @Value("${admin.secret}")
    private String adminSecret;

    public void check(String headerSecret) {
        if (headerSecret == null || !headerSecret.equals(adminSecret)) {
            throw new AdminAccessDeniedException();
        }
    }
}
