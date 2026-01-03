package com.adventure.lessonservice.controller;

import com.adventure.lessonservice.repository.LessonRepository;
import com.adventure.lessonservice.security.AdminGuard;
import com.adventure.lessonservice.service.CodeExecutionService;
import com.adventure.lessonservice.service.LessonProgressService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LessonController.class)
class LessonAdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LessonRepository lessonRepository;

    @MockBean
    private LessonProgressService lessonProgressService;

    @MockBean
    private CodeExecutionService codeExecutionService;

    @MockBean
    private AdminGuard adminGuard;

    // -----------------------------------------
    // ❌ CREATE — header ausente → 400
    // -----------------------------------------
    @Test
    void shouldReturn400WhenAdminSecretIsMissingOnCreate() throws Exception {
        mockMvc.perform(post("/lessons")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest());
    }

    // -----------------------------------------
    // ✅ CREATE — admin secret válido → 200
    // -----------------------------------------
    @Test
    void shouldAllowCreateLessonWithValidAdminSecret() throws Exception {
        doNothing().when(adminGuard).check("secret");

        mockMvc.perform(post("/lessons")
                .header("X-Admin-Secret", "secret")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "title": "Lesson 1",
                      "content": "Hello",
                      "expectedOutput": "Hello"
                    }
                """))
                .andExpect(status().isOk());
    }

    // -----------------------------------------
    // ❌ UPDATE — header ausente → 400
    // -----------------------------------------
    @Test
    void shouldReturn400WhenAdminSecretIsMissingOnUpdate() throws Exception {
        mockMvc.perform(put("/lessons/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest());
    }

    // -----------------------------------------
    // ❌ DELETE — header ausente → 400
    // -----------------------------------------
    @Test
    void shouldReturn400WhenAdminSecretIsMissingOnDelete() throws Exception {
        mockMvc.perform(delete("/lessons/1"))
                .andExpect(status().isBadRequest());
    }
}
