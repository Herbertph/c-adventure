package com.adventure.lessonservice.service;


import com.adventure.lessonservice.model.LessonProgress;
import com.adventure.lessonservice.repository.LessonProgressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonProgressService {

    @Autowired
    private LessonProgressRepository progressRepository;

    public void markAsCompleted(Long userId, Long lessonId) {
        LessonProgress progress = progressRepository
                .findByUserIdAndLessonId(userId, lessonId)
                .orElse(new LessonProgress(null, userId, lessonId, false));

        progress.setCompleted(true);
        progressRepository.save(progress);
    }

     public boolean hasCompleted(Long userId, Long lessonId) {
        return progressRepository
                .existsByUserIdAndLessonIdAndCompletedTrue(userId, lessonId);
    }

    

    public List<Long> getCompletedLessonIds(Long userId) {
        return progressRepository.findByUserId(userId).stream()
                .filter(LessonProgress::isCompleted)
                .map(LessonProgress::getLessonId)
                .toList();
    }
}
