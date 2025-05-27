package com.adventure.auth.dto;

import lombok.Data;

@Data
public class UserResponse {
    private String firstName;
    private String lastName;
    private String email;
}
