package com.adventure.lessonservice.controller;

import com.adventure.lessonservice.dto.CodeExecutionRequest;
import com.adventure.lessonservice.model.Lesson;
import com.adventure.lessonservice.repository.LessonRepository;
import com.adventure.lessonservice.service.CodeExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/execute")
public class CodeExecutionController {

    @Autowired
    private CodeExecutionService service;

    @Autowired
    private LessonRepository lessonRepository;

    @PostMapping
    public ResponseEntity<Map<String, Object>> execute(@RequestBody CodeExecutionRequest request) {

        String output = service.executeCode(request.language, request.code, request.input);

        Lesson lesson = lessonRepository.findById(request.lessonId).orElse(null);
        if (lesson == null) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("expected", "N/A");
            error.put("actual", "Lição não encontrada");
            return ResponseEntity.badRequest().body(error);
        }

        String expected = lesson.getExpectedOutput();

        boolean success = output.trim().equals(expected.trim());

        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("expected", expected);
        response.put("actual", output);

        return ResponseEntity.ok(response);
    }
}
