package com.adventure.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message = "First name is required")
    @Size(min = 3, message = "First name must have at least 3 letters")
    @Pattern(regexp = "^[A-Za-zÀ-ÿ\\s]+$", message = "First name must contain only letters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 3, message = "Last name must have at least 3 letters")
    @Pattern(regexp = "^[A-Za-zÀ-ÿ\\s]+$", message = "Last name must contain only letters")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{10,}$",
            message = "Password must be at least 10 characters, include a letter, a number and a special character"
    )
    private String password;

    @NotBlank(message = "Password confirmation is required")
    private String confirmPassword;
}
