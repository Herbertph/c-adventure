package com.adventure.lessonservice.controller;

import com.adventure.lessonservice.dto.SubmissionRequest;
import com.adventure.lessonservice.model.Lesson;
import com.adventure.lessonservice.repository.LessonRepository;
import com.adventure.lessonservice.security.SecurityUtils;
import com.adventure.lessonservice.service.CodeExecutionService;
import com.adventure.lessonservice.service.LessonProgressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return lessonRepository.findAllByOrderByOrderIndexAsc();
    }

    // GET /lessons/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Lesson> getById(@PathVariable Long id) {

        Lesson lesson = lessonRepository.findById(id).orElse(null);
        if (lesson == null) {
            return ResponseEntity.notFound().build();
        }

        // Primeira lição é sempre pública
        if (lesson.getOrderIndex() == 1) {
            return ResponseEntity.ok(lesson);
        }

        // A partir da segunda, exige login
        String userId = SecurityUtils.getCurrentUserId();
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Busca a lição anterior pelo orderIndex
        Lesson previousLesson = lessonRepository
                .findByOrderIndex(lesson.getOrderIndex() - 1)
                .orElse(null);

        if (previousLesson == null) {
            // erro de consistência de dados
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        boolean hasPrevious = progressService.hasCompleted(
                userId,
                previousLesson.getId()
        );

        if (!hasPrevious) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(lesson);
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

        if (passed) {
            String userId = SecurityUtils.getCurrentUserId();
            if (userId != null) {
                progressService.markAsCompleted(userId, lesson.getId());
            }
        }

        return ResponseEntity.ok(
                Map.of(
                        "success", passed,
                        "expected", lesson.getExpectedOutput(),
                        "actual", output
                )
        );
    }

    // CREATE (temporário, sem segurança)
    @PostMapping
    public Lesson createLesson(@RequestBody Lesson lesson) {
        return lessonRepository.save(lesson);
    }

    // UPDATE (temporário, sem segurança)
    @PutMapping("/{id}")
    public ResponseEntity<Lesson> updateLesson(
            @PathVariable Long id,
            @RequestBody Lesson lesson
    ) {
        if (!lessonRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        lesson.setId(id);
        return ResponseEntity.ok(lessonRepository.save(lesson));
    }

    // DELETE (temporário, sem segurança)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLesson(@PathVariable Long id) {
        if (!lessonRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        lessonRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
