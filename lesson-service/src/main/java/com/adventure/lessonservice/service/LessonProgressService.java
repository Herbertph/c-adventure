package com.adventure.lessonservice.service;

import com.adventure.lessonservice.model.LessonProgress;
import com.adventure.lessonservice.repository.LessonProgressRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonProgressService {

    private final LessonProgressRepository repository;

    public LessonProgressService(LessonProgressRepository repository) {
        this.repository = repository;
    }

    // Marca lição como concluída
    public void markAsCompleted(Long userId, Long lessonId) {
        LessonProgress progress = repository
                .findByUserIdAndLessonId(userId, lessonId)
                .orElseGet(() -> LessonProgress.builder()
                        .userId(userId)
                        .lessonId(lessonId)
                        .completed(false)
                        .build()
                );

        progress.setCompleted(true);
        repository.save(progress);
    }

    // Retorna IDs das lições concluídas
    public List<Long> getCompletedLessonIds(Long userId) {
        return repository.findByUserIdAndCompletedTrue(userId)
                .stream()
                .map(LessonProgress::getLessonId)
                .toList();
    }

    // Verifica se lição foi concluída
    public boolean hasCompleted(Long userId, Long lessonId) {
        return repository
                .findByUserIdAndLessonId(userId, lessonId)
                .map(LessonProgress::isCompleted)
                .orElse(false);
    }
}
