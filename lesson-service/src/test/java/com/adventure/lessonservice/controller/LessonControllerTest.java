package com.adventure.lessonservice.controller;

import com.adventure.lessonservice.dto.SubmissionRequest;
import com.adventure.lessonservice.model.Lesson;
import com.adventure.lessonservice.repository.LessonRepository;
import com.adventure.lessonservice.service.CodeExecutionService;
import com.adventure.lessonservice.service.LessonProgressService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class LessonControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    private LessonRepository lessonRepository;
    private LessonProgressService progressService;
    private CodeExecutionService codeExecutionService;

    private static final String USER_ID = "user-1";

    @BeforeEach
    void setup() {
        lessonRepository = Mockito.mock(LessonRepository.class);
        progressService = Mockito.mock(LessonProgressService.class);
        codeExecutionService = Mockito.mock(CodeExecutionService.class);

        objectMapper = new ObjectMapper();

        LessonController controller = new LessonController(
                lessonRepository,
                codeExecutionService,
                progressService
        );

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    private void mockAuthenticatedUser() {
        var auth = new UsernamePasswordAuthenticationToken(
                USER_ID,
                null,
                List.of()
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    // -------------------------
    // GET /lessons
    // -------------------------

    @Test
    void shouldReturnAllLessons() throws Exception {
        Mockito.when(lessonRepository.findAll())
                .thenReturn(List.of(new Lesson(), new Lesson()));

        mockMvc.perform(get("/lessons"))
                .andExpect(status().isOk());
    }

    // -------------------------
    // GET /lessons/{id}
    // -------------------------

    @Test
    void shouldReturnLesson1WithoutAuth() throws Exception {
        Lesson lesson = new Lesson();
        lesson.setId(1L);

        Mockito.when(lessonRepository.findById(1L))
                .thenReturn(Optional.of(lesson));

        mockMvc.perform(get("/lessons/1"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturn401WhenAccessingLesson2WithoutAuth() throws Exception {
        Lesson lesson = new Lesson();
        lesson.setId(2L);

        Mockito.when(lessonRepository.findById(2L))
                .thenReturn(Optional.of(lesson));

        mockMvc.perform(get("/lessons/2"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldReturn403WhenPreviousLessonNotCompleted() throws Exception {
        mockAuthenticatedUser();

        Lesson lesson = new Lesson();
        lesson.setId(2L);

        Mockito.when(lessonRepository.findById(2L))
                .thenReturn(Optional.of(lesson));

        Mockito.when(progressService.hasCompleted(USER_ID, 1L))
                .thenReturn(false);

        mockMvc.perform(get("/lessons/2"))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldReturn200WhenPreviousLessonCompleted() throws Exception {
        mockAuthenticatedUser();

        Lesson lesson = new Lesson();
        lesson.setId(2L);

        Mockito.when(lessonRepository.findById(2L))
                .thenReturn(Optional.of(lesson));

        Mockito.when(progressService.hasCompleted(USER_ID, 1L))
                .thenReturn(true);

        mockMvc.perform(get("/lessons/2"))
                .andExpect(status().isOk());
    }

    @Test
void shouldReturn401WhenLessonDoesNotExistAndUserNotAuthenticated() throws Exception {
    Mockito.when(lessonRepository.findById(999L))
            .thenReturn(Optional.empty());

    mockMvc.perform(get("/lessons/999"))
            .andExpect(status().isUnauthorized());
}


    // -------------------------
    // POST /lessons/submit
    // -------------------------

    @Test
    void shouldReturn400WhenSubmittingNonExistingLesson() throws Exception {
        SubmissionRequest request = new SubmissionRequest();
        request.lessonId = 1L;

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
        request.language = "csharp";
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
        request.language = "csharp";
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
}
