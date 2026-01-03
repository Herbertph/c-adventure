package com.adventure.lessonservice.controller;

import com.adventure.lessonservice.model.Lesson;
import com.adventure.lessonservice.repository.LessonRepository;
import com.adventure.lessonservice.security.AdminGuard;
import com.adventure.lessonservice.service.CodeExecutionService;
import com.adventure.lessonservice.service.LessonProgressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class LessonControllerTest {

    private MockMvc mockMvc;

    private LessonRepository lessonRepository;
    private LessonProgressService progressService;
    private CodeExecutionService codeExecutionService;
    private AdminGuard adminGuard;

    @BeforeEach
    void setup() {
        lessonRepository = Mockito.mock(LessonRepository.class);
        progressService = Mockito.mock(LessonProgressService.class);
        codeExecutionService = Mockito.mock(CodeExecutionService.class);
        adminGuard = Mockito.mock(AdminGuard.class);

        LessonController controller = new LessonController(
                lessonRepository,
                codeExecutionService,
                progressService,
                adminGuard
        );

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
    }

    @Test
    void shouldReturn200WhenLessonExists() throws Exception {
        Lesson lesson = new Lesson();
        lesson.setId(1L);

        Mockito.when(lessonRepository.findById(1L))
                .thenReturn(Optional.of(lesson));

        mockMvc.perform(
                        get("/lessons/1")
                                .header("X-User-Id", "1")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturn403WhenPreviousLessonNotCompleted() throws Exception {
        Lesson lesson = new Lesson();
        lesson.setId(2L);

        Mockito.when(lessonRepository.findById(2L))
                .thenReturn(Optional.of(lesson));

        Mockito.when(progressService.hasCompleted(eq(1L), eq(1L)))
                .thenReturn(false);

        mockMvc.perform(
                        get("/lessons/2")
                                .header("X-User-Id", "1")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldReturn200WhenPreviousLessonCompleted() throws Exception {
        Lesson lesson = new Lesson();
        lesson.setId(2L);

        Mockito.when(lessonRepository.findById(2L))
                .thenReturn(Optional.of(lesson));

        Mockito.when(progressService.hasCompleted(eq(1L), eq(1L)))
                .thenReturn(true);

        mockMvc.perform(
                        get("/lessons/2")
                                .header("X-User-Id", "1")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
void shouldReturn404WhenLessonDoesNotExist() throws Exception {
    Mockito.when(progressService.hasCompleted(eq(1L), eq(998L)))
            .thenReturn(true);

    Mockito.when(lessonRepository.findById(999L))
            .thenReturn(Optional.empty());

    mockMvc.perform(
                    get("/lessons/999")
                            .header("X-User-Id", "1")
                            .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isNotFound());
}
}
