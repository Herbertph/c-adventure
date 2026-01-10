package com.adventure.lessonservice.service;

import com.adventure.lessonservice.dto.LessonUpdateRequest;
import com.adventure.lessonservice.model.Lesson;
import com.adventure.lessonservice.repository.LessonRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LessonService {

    private final LessonRepository lessonRepository;

    public LessonService(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    public Lesson updateLesson(Long id, LessonUpdateRequest request) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lesson not found"));

        if (request.title() != null) {
            lesson.setTitle(request.title());
        }

        if (request.description() != null) {
            lesson.setDescription(request.description());
        }

        if (request.initialCode() != null) {
            lesson.setInitialCode(request.initialCode());
        }

        if (request.expectedOutput() != null) {
            lesson.setExpectedOutput(request.expectedOutput());
        }

        return lessonRepository.save(lesson);
    }
    public void deleteLesson(Long id) {
        if (!lessonRepository.existsById(id)) {
            throw new EntityNotFoundException("Lesson not found");
        }

        lessonRepository.deleteById(id);
    }
}
