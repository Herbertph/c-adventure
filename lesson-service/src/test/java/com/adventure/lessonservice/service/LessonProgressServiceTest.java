package com.adventure.lessonservice.service;

import com.adventure.lessonservice.model.LessonProgress;
import com.adventure.lessonservice.repository.LessonProgressRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LessonProgressServiceTest {

    @Mock
    private LessonProgressRepository lessonProgressRepository;

    @InjectMocks
    private LessonProgressService lessonProgressService;

    @Test
    void shouldAllowAccessIfLessonCompleted() {
        Long userId = 1L;
        Long lessonId = 1L;

        LessonProgress progress = LessonProgress.builder()
                .userId(userId)
                .lessonId(lessonId)
                .completed(true)
                .build();

        when(lessonProgressRepository.findByUserIdAndLessonId(userId, lessonId))
                .thenReturn(Optional.of(progress));

        boolean result = lessonProgressService.hasCompleted(userId, lessonId);

        assertTrue(result);

        verify(lessonProgressRepository)
                .findByUserIdAndLessonId(userId, lessonId);
    }

    @Test
    void shouldDenyAccessIfLessonNotCompleted() {
        Long userId = 1L;
        Long lessonId = 1L;

        LessonProgress progress = LessonProgress.builder()
                .userId(userId)
                .lessonId(lessonId)
                .completed(false)
                .build();

        when(lessonProgressRepository.findByUserIdAndLessonId(userId, lessonId))
                .thenReturn(Optional.of(progress));

        boolean result = lessonProgressService.hasCompleted(userId, lessonId);

        assertFalse(result);
    }

    @Test
    void shouldDenyAccessIfNoProgressExists() {
        Long userId = 1L;
        Long lessonId = 1L;

        when(lessonProgressRepository.findByUserIdAndLessonId(userId, lessonId))
                .thenReturn(Optional.empty());

        boolean result = lessonProgressService.hasCompleted(userId, lessonId);

        assertFalse(result);
    }
}
