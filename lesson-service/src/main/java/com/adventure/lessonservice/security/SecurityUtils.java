package com.adventure.lessonservice.security;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtils {

    private SecurityUtils() {}

    public static Long getCurrentUserId() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    if (auth == null || !auth.isAuthenticated()) {
        throw new AccessDeniedException("Usuário não autenticado");
    }

    Object principal = auth.getPrincipal();

    if (principal instanceof String userId) {
        return Long.valueOf(userId);
    }

    throw new AccessDeniedException("Principal inválido");
}
}
