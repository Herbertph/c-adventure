package com.adventure.lessonservice.controller;

import com.adventure.lessonservice.dto.SubmissionRequest;
import com.adventure.lessonservice.model.Lesson;
import com.adventure.lessonservice.repository.LessonRepository;
import com.adventure.lessonservice.service.CodeExecutionService;
import com.adventure.lessonservice.service.LessonProgressService;
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

    // GET /lessons → Lista todas as lições
    @GetMapping
    public List<Lesson> getAll() {
        return lessonRepository.findAll();
    }

    // 🔒 GET /lessons/{id} → Busca lição com bloqueio por progresso
    @GetMapping("/{id}")
    public ResponseEntity<Lesson> getById(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long userId
    ) {
        System.out.println("Acessando lição " + id + " para userId: " + userId);
        if (id > 1) {
            boolean hasPrevious =
                    progressService.hasCompleted(userId, id - 1);
        System.out.println("Liçao anterior ( " + (id - 1) + ") concluída? " + hasPrevious);
            if (!hasPrevious) {
                System.out.println("Bloqueando acesso à lição " + id);
                return ResponseEntity.status(403).build();
            }
        }

        return lessonRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /lessons/submit → Submete código
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

    // POST /lessons → Criar lição
    @PostMapping
    public Lesson createLesson(@RequestBody Lesson lesson) {
        return lessonRepository.save(lesson);
    }

    // PUT /lessons/{id}
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

    // DELETE /lessons/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLesson(@PathVariable Long id) {
        if (!lessonRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        lessonRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
