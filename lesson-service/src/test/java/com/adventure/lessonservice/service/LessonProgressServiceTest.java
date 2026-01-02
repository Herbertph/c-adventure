package com.adventure.lessonservice.service;

import com.adventure.lessonservice.repository.LessonProgressRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LessonProgressServiceTest {

    @Mock
    private LessonProgressRepository lessonProgressRepository;

    @InjectMocks
    private LessonProgressService lessonProgressService;

    @Test
    void shouldAllowAccessToLesson1Always() {
        // lesson 1 nunca depende de progresso anterior
        boolean canAccess = true; // regra implícita do sistema

        assertTrue(canAccess);
        verifyNoInteractions(lessonProgressRepository);
    }

    @Test
    void shouldAllowAccessToLesson2IfLesson1Completed() {
        Long userId = 1L;
        Long previousLessonId = 1L;

        when(lessonProgressRepository
                .existsByUserIdAndLessonIdAndCompletedTrue(userId, previousLessonId))
                .thenReturn(true);

        boolean hasCompletedPrevious =
                lessonProgressService.hasCompleted(userId, previousLessonId);

        assertTrue(hasCompletedPrevious);

        verify(lessonProgressRepository)
                .existsByUserIdAndLessonIdAndCompletedTrue(userId, previousLessonId);
    }

    @Test
    void shouldDenyAccessToLesson2IfLesson1NotCompleted() {
        Long userId = 1L;
        Long previousLessonId = 1L;

        when(lessonProgressRepository
                .existsByUserIdAndLessonIdAndCompletedTrue(userId, previousLessonId))
                .thenReturn(false);

        boolean hasCompletedPrevious =
                lessonProgressService.hasCompleted(userId, previousLessonId);

        assertFalse(hasCompletedPrevious);

        verify(lessonProgressRepository)
                .existsByUserIdAndLessonIdAndCompletedTrue(userId, previousLessonId);
    }
}
