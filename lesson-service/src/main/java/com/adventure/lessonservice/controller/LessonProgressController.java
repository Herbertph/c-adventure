package com.adventure.lessonservice.controller;

import com.adventure.lessonservice.dto.ProgressRequest;
import com.adventure.lessonservice.model.Lesson;
import com.adventure.lessonservice.service.LessonProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/progress")
public class LessonProgressController {

    @Autowired
    private LessonProgressService progressService;

    @PostMapping
    public ResponseEntity<?> markAsCompleted(@RequestBody ProgressRequest request) {
        progressService.markAsCompleted(request.getUserId(), request.getLessonId());
        return ResponseEntity.ok("Progresso salvo com sucesso.");
    }

    @GetMapping("/{userId}")
    public List<Long> getCompletedLessons(@PathVariable Long userId) {
        return progressService.getCompletedLessonIds(userId);
    }

   
}
