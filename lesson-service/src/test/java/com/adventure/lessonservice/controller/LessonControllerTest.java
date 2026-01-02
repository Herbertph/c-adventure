package com.adventure.lessonservice.controller;

import com.adventure.lessonservice.model.Lesson;
import com.adventure.lessonservice.repository.LessonRepository;
import com.adventure.lessonservice.service.CodeExecutionService;
import com.adventure.lessonservice.service.LessonProgressService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LessonController.class)
class LessonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LessonRepository lessonRepository;

    @MockBean
    private CodeExecutionService codeExecutionService;

    @MockBean
    private LessonProgressService lessonProgressService;

    // --------------------------------------------------
    // GET /lessons
    // --------------------------------------------------
    @Test
    void shouldReturnAllLessons() throws Exception {
        Lesson lesson = new Lesson();
        lesson.setId(1L);
        lesson.setTitle("Intro");

        when(lessonRepository.findAll())
                .thenReturn(List.of(lesson));

        mockMvc.perform(get("/lessons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    // --------------------------------------------------
    // GET /lessons/{id} – acesso permitido
    // --------------------------------------------------
    @Test
    void shouldReturnLessonWhenPreviousCompleted() throws Exception {
        Lesson lesson = new Lesson();
        lesson.setId(2L);
        lesson.setTitle("Lesson 2");

        when(lessonProgressService.hasCompleted(1L, 1L))
                .thenReturn(true);

        when(lessonRepository.findById(2L))
                .thenReturn(Optional.of(lesson));

        mockMvc.perform(get("/lessons/2")
                        .header("X-User-Id", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2));
    }

    // --------------------------------------------------
    // GET /lessons/{id} – acesso BLOQUEADO
    // --------------------------------------------------
    @Test
    void shouldBlockLessonWhenPreviousNotCompleted() throws Exception {
        when(lessonProgressService.hasCompleted(1L, 1L))
                .thenReturn(false);

        mockMvc.perform(get("/lessons/2")
                        .header("X-User-Id", 1L))
                .andExpect(status().isForbidden());
    }

    // --------------------------------------------------
    // POST /lessons/submit
    // --------------------------------------------------
    @Test
    void shouldReturnSuccessWhenCodeMatchesExpectedOutput() throws Exception {
        Lesson lesson = new Lesson();
        lesson.setId(1L);
        lesson.setExpectedOutput("Hello");

        when(lessonRepository.findById(1L))
                .thenReturn(Optional.of(lesson));

        when(codeExecutionService.executeCode(any(), any(), any()))
                .thenReturn("Hello");

        String body = """
            {
              "lessonId": 1,
              "language": "java",
              "code": "System.out.print(\\"Hello\\");",
              "input": ""
            }
        """;

        mockMvc.perform(post("/lessons/submit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }
}
