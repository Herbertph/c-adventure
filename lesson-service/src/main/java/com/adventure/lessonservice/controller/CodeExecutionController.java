package com.adventure.lessonservice.controller;
import com.adventure.lessonservice.dto.CodeExecutionRequest;
import com.adventure.lessonservice.service.CodeExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/execute")
public class CodeExecutionController {

    @Autowired
    private CodeExecutionService service;

    @PostMapping
    public ResponseEntity<String> execute(@RequestBody CodeExecutionRequest request) {
        String output = service.executeCode(request.language, request.code, request.input);
        return ResponseEntity.ok(output);
    }
}
