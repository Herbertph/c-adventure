package com.adventure.lessonservice.controller;

import com.adventure.lessonservice.model.Lesson;
import com.adventure.lessonservice.repository.LessonRepository;
import com.adventure.lessonservice.security.AdminGuard;
import com.adventure.lessonservice.service.CodeExecutionService;
import com.adventure.lessonservice.service.LessonProgressService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LessonController.class)
class LessonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LessonRepository lessonRepository;

    @MockBean
    private LessonProgressService lessonProgressService;

    @MockBean
    private CodeExecutionService codeExecutionService;

    // 🔑 NOVO — necessário por causa do construtor
    @MockBean
    private AdminGuard adminGuard;

    // -----------------------------------------
    // ✅ ACCESS GRANTED: lesson 1 (sempre livre)
    // -----------------------------------------
    @Test
    void shouldAllowAccessToFirstLesson() throws Exception {
        Lesson lesson = new Lesson();
        lesson.setId(1L);

        when(lessonRepository.findById(1L))
                .thenReturn(Optional.of(lesson));

        mockMvc.perform(
                get("/lessons/1")
                        .header("X-User-Id", 1L)
        )
        .andExpect(status().isOk());
    }

    // -----------------------------------------
    // ❌ ACCESS DENIED: previous lesson not done
    // -----------------------------------------
    @Test
    void shouldBlockLessonWhenPreviousNotCompleted() throws Exception {
        when(lessonProgressService.hasCompleted(1L, 1L))
                .thenReturn(false);

        mockMvc.perform(
                get("/lessons/2")
                        .header("X-User-Id", 1L)
        )
        .andExpect(status().isForbidden());
    }

    // -----------------------------------------
    // ✅ ACCESS GRANTED: previous lesson completed
    // -----------------------------------------
    @Test
    void shouldAllowLessonWhenPreviousCompleted() throws Exception {
        Lesson lesson = new Lesson();
        lesson.setId(2L);

        when(lessonProgressService.hasCompleted(1L, 1L))
                .thenReturn(true);

        when(lessonRepository.findById(2L))
                .thenReturn(Optional.of(lesson));

        mockMvc.perform(
                get("/lessons/2")
                        .header("X-User-Id", 1L)
        )
        .andExpect(status().isOk());
    }

    // -----------------------------------------
    // ❌ NOT FOUND: lesson does not exist
    // -----------------------------------------
    @Test
    void shouldReturn404WhenLessonDoesNotExist() throws Exception {
        when(lessonProgressService.hasCompleted(1L, 1L))
                .thenReturn(true);

        when(lessonRepository.findById(2L))
                .thenReturn(Optional.empty());

        mockMvc.perform(
                get("/lessons/2")
                        .header("X-User-Id", 1L)
        )
        .andExpect(status().isNotFound());
    }
}
