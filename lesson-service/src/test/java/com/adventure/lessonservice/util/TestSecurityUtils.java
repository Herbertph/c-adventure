package com.adventure.lessonservice.util;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;

public final class TestSecurityUtils {

    private TestSecurityUtils() {}

    public static void mockUser(String userId) {
        var auth = new UsernamePasswordAuthenticationToken(
                userId,          
                null,
                Collections.emptyList()
        );

        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    public static void clear() {
        SecurityContextHolder.clearContext();
    }
}
