package com.adventure.lessonservice.dto;

import lombok.Data;

@Data
public class ProgressRequest {
    private String userId;
    private Long lessonId;
}
