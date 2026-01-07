package com.adventure.lessonservice.controller;

import com.adventure.lessonservice.dto.ProgressRequest;
import com.adventure.lessonservice.service.LessonProgressService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/progress")
public class LessonProgressController {

    private final LessonProgressService progressService;

    public LessonProgressController(LessonProgressService progressService) {
        this.progressService = progressService;
    }

    @PostMapping
    public ResponseEntity<?> markAsCompleted(
            @RequestBody ProgressRequest request,
            Authentication authentication
    ) {
        if (!(authentication instanceof JwtAuthenticationToken jwtAuth)) {
            return ResponseEntity.status(401).body("Token JWT inválido");
        }

       
        String userId = jwtAuth.getToken().getClaimAsString("sub");

        progressService.markAsCompleted(userId, request.getLessonId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}")
    public List<Long> getCompletedLessons(@PathVariable String userId) {
        return progressService.getCompletedLessonIds(userId);
    }
}
