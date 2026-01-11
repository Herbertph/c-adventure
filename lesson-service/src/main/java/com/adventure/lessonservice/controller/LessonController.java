package com.adventure.lessonservice.controller;

import com.adventure.lessonservice.dto.SubmissionRequest;
import com.adventure.lessonservice.model.Lesson;
import com.adventure.lessonservice.repository.LessonRepository;
import com.adventure.lessonservice.service.CodeExecutionService;
import com.adventure.lessonservice.service.LessonProgressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import com.adventure.lessonservice.security.SecurityUtils;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/lessons")
public class LessonController {

    private final LessonRepository lessonRepository;
    private final CodeExecutionService codeExecutionService;
    private final LessonProgressService progressService;

    public LessonController(
            LessonRepository lessonRepository,
            CodeExecutionService codeExecutionService,
            LessonProgressService progressService
    ) {
        this.lessonRepository = lessonRepository;
        this.codeExecutionService = codeExecutionService;
        this.progressService = progressService;
    
    }

    // GET /lessons
    @GetMapping
    public List<Lesson> getAll() {
        return lessonRepository.findAll();
    }

    // 🔒 GET /lessons/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Lesson> getById(@PathVariable Long id) {

        // Lição 1 é pública
        if (id == 1) {
            return lessonRepository.findById(1L)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }

        // A partir da lição 2 exige login
        String userId = SecurityUtils.getCurrentUserId();

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        boolean hasPrevious =
                progressService.hasCompleted(userId, id - 1);

        if (!hasPrevious) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return lessonRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /lessons/submit
    @PostMapping("/submit")
    public ResponseEntity<?> submitCode(@RequestBody SubmissionRequest request) {
        Lesson lesson = lessonRepository.findById(request.lessonId).orElse(null);

        if (lesson == null) {
            return ResponseEntity.badRequest().body("Lição não encontrada.");
        }

        String output = codeExecutionService.executeCode(
                request.language,
                request.code,
                request.input
        );

        boolean passed = output.trim()
                .equalsIgnoreCase(lesson.getExpectedOutput().trim());

        return ResponseEntity.ok(
                Map.of(
                        "success", passed,
                        "expected", lesson.getExpectedOutput(),
                        "actual", output
                )
        );
    }
}
