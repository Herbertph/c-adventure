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

    private static final String USER_ID = "user-1";

    @Test
    void shouldAllowAccessIfLessonCompleted() {
        Long lessonId = 1L;

        LessonProgress progress = LessonProgress.builder()
                .userId(USER_ID)
                .lessonId(lessonId)
                .completed(true)
                .build();

        when(lessonProgressRepository.findByUserIdAndLessonId(USER_ID, lessonId))
                .thenReturn(Optional.of(progress));

        boolean result = lessonProgressService.hasCompleted(USER_ID, lessonId);

        assertTrue(result);

        verify(lessonProgressRepository)
                .findByUserIdAndLessonId(USER_ID, lessonId);
    }

    @Test
    void shouldDenyAccessIfLessonNotCompleted() {
        Long lessonId = 1L;

        LessonProgress progress = LessonProgress.builder()
                .userId(USER_ID)
                .lessonId(lessonId)
                .completed(false)
                .build();

        when(lessonProgressRepository.findByUserIdAndLessonId(USER_ID, lessonId))
                .thenReturn(Optional.of(progress));

        boolean result = lessonProgressService.hasCompleted(USER_ID, lessonId);

        assertFalse(result);
    }

    @Test
    void shouldDenyAccessIfNoProgressExists() {
        Long lessonId = 1L;

        when(lessonProgressRepository.findByUserIdAndLessonId(USER_ID, lessonId))
                .thenReturn(Optional.empty());

        boolean result = lessonProgressService.hasCompleted(USER_ID, lessonId);

        assertFalse(result);
    }
}
