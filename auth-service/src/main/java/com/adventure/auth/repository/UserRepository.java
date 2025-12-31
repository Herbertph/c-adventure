// src/main/java/com/adventure/auth/repository/UserRepository.java
package com.adventure.auth.repository;

import com.adventure.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
