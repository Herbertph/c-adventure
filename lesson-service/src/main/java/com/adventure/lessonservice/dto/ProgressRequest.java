package com.adventure.lessonservice.dto;

import lombok.Data;

@Data
public class ProgressRequest {
    private Long userId;
    private Long lessonId;
}
