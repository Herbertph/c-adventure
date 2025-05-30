package com.adventure.auth.dto;

import lombok.Data;

@Data
public class UserResponse {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
}
