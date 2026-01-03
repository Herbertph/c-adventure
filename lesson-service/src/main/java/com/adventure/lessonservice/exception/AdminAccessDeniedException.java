package com.adventure.lessonservice.exception;

public class AdminAccessDeniedException extends RuntimeException {
    public AdminAccessDeniedException() {
        super("Admin access denied");
    }
}
