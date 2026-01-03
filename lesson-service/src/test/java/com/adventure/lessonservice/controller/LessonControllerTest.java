package com.adventure.lessonservice.controller;

import com.adventure.lessonservice.dto.SubmissionRequest;
import com.adventure.lessonservice.model.Lesson;
import com.adventure.lessonservice.repository.LessonRepository;
import com.adventure.lessonservice.security.AdminGuard;
import com.adventure.lessonservice.service.CodeExecutionService;
import com.adventure.lessonservice.service.LessonProgressService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class LessonControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

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

        // Admin sempre autorizado
        Mockito.doNothing().when(adminGuard).check(anyString());

        objectMapper = new ObjectMapper();

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

    // -------------------------
    // GET /lessons/{id}
    // -------------------------

    @Test
    void shouldReturn200WhenLessonExists() throws Exception {
        Lesson lesson = new Lesson();
        lesson.setId(1L);

        Mockito.when(lessonRepository.findById(1L))
                .thenReturn(Optional.of(lesson));

        mockMvc.perform(
                        get("/lessons/1")
                                .header("X-User-Id", "1")
                )
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturn403WhenPreviousLessonNotCompleted() throws Exception {
        Lesson lesson = new Lesson();
        lesson.setId(2L);

        Mockito.when(lessonRepository.findById(2L))
                .thenReturn(Optional.of(lesson));

        Mockito.when(progressService.hasCompleted(1L, 1L))
                .thenReturn(false);

        mockMvc.perform(
                        get("/lessons/2")
                                .header("X-User-Id", "1")
                )
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldReturn200WhenPreviousLessonCompleted() throws Exception {
        Lesson lesson = new Lesson();
        lesson.setId(2L);

        Mockito.when(lessonRepository.findById(2L))
                .thenReturn(Optional.of(lesson));

        Mockito.when(progressService.hasCompleted(1L, 1L))
                .thenReturn(true);

        mockMvc.perform(
                        get("/lessons/2")
                                .header("X-User-Id", "1")
                )
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturn404WhenLessonDoesNotExist() throws Exception {
        Mockito.when(progressService.hasCompleted(1L, 998L))
                .thenReturn(true);

        Mockito.when(lessonRepository.findById(999L))
                .thenReturn(Optional.empty());

        mockMvc.perform(
                        get("/lessons/999")
                                .header("X-User-Id", "1")
                )
                .andExpect(status().isNotFound());
    }

    // -------------------------
    // POST /lessons/submit
    // -------------------------

    @Test
    void shouldReturn400WhenSubmittingNonExistingLesson() throws Exception {
        SubmissionRequest request = new SubmissionRequest();
        request.lessonId = 1L;
        request.language = "java";
        request.code = "code";
        request.input = "";

        Mockito.when(lessonRepository.findById(1L))
                .thenReturn(Optional.empty());

        mockMvc.perform(
                        post("/lessons/submit")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnSuccessTrueWhenCodeIsCorrect() throws Exception {
        Lesson lesson = new Lesson();
        lesson.setId(1L);
        lesson.setExpectedOutput("42");

        SubmissionRequest request = new SubmissionRequest();
        request.lessonId = 1L;
        request.language = "java";
        request.code = "code";
        request.input = "";

        Mockito.when(lessonRepository.findById(1L))
                .thenReturn(Optional.of(lesson));

        Mockito.when(codeExecutionService.executeCode(any(), any(), any()))
                .thenReturn("42");

        mockMvc.perform(
                        post("/lessons/submit")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    void shouldReturnSuccessFalseWhenCodeIsIncorrect() throws Exception {
        Lesson lesson = new Lesson();
        lesson.setId(1L);
        lesson.setExpectedOutput("42");

        SubmissionRequest request = new SubmissionRequest();
        request.lessonId = 1L;
        request.language = "java";
        request.code = "code";
        request.input = "";

        Mockito.when(lessonRepository.findById(1L))
                .thenReturn(Optional.of(lesson));

        Mockito.when(codeExecutionService.executeCode(any(), any(), any()))
                .thenReturn("41");

        mockMvc.perform(
                        post("/lessons/submit")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false));
    }

    // -------------------------
    // ADMIN
    // -------------------------

    @Test
    void shouldCreateLessonWhenAdminIsValid() throws Exception {
        Lesson lesson = new Lesson();
        lesson.setId(1L);

        Mockito.when(lessonRepository.save(any()))
                .thenReturn(lesson);

        mockMvc.perform(
                        post("/lessons")
                                .header("X-Admin-Secret", "secret")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(lesson))
                )
                .andExpect(status().isOk());
    }

    @Test
    void shouldUpdateLessonWhenAdminIsValid() throws Exception {
        Lesson lesson = new Lesson();
        lesson.setId(1L);

        Mockito.when(lessonRepository.existsById(1L))
                .thenReturn(true);

        Mockito.when(lessonRepository.save(any()))
                .thenReturn(lesson);

        mockMvc.perform(
                        put("/lessons/1")
                                .header("X-Admin-Secret", "secret")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(lesson))
                )
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteLessonWhenAdminIsValid() throws Exception {
        Mockito.when(lessonRepository.existsById(1L))
                .thenReturn(true);

        mockMvc.perform(
                        delete("/lessons/1")
                                .header("X-Admin-Secret", "secret")
                )
                .andExpect(status().isNoContent());
    }
}
