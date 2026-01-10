package com.adventure.lessonservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LessonUpdateRequest {
    private String title;
    private String description;
    private String initialCode;
    private String expectedOutput;
}
