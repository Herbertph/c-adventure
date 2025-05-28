package com.adventure.lessonservice.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CodeExecutionService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String pistonUrl = "https://emkc.org/api/v2/piston/execute";

    public String executeCode(String language, String code, String input) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("language", language);
        payload.put("version", "*");
        payload.put("stdin", input);

        Map<String, String> file = new HashMap<>();
        file.put("name", "Main.cs");
        file.put("content", code);

        payload.put("files", List.of(file));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(pistonUrl, request, Map.class);
            Map<String, Object> body = response.getBody();

            if (body != null) {
                System.out.println("🟢 RESPOSTA COMPLETA DO PISTON:");
                System.out.println(body);
                
                if (body.containsKey("output")) {
                    return body.get("output").toString();
                } else if (body.containsKey("run")) {
                    Map<String, Object> run = (Map<String, Object>) body.get("run");
                    return run.get("output").toString();
                } else {
                    return "Erro: estrutura de resposta não reconhecida: " + body;
                }
            } else {
                return "Erro: corpo da resposta vazio.";
            }

        } catch (Exception e) {
            return "Erro ao executar código: " + e.getMessage();
        }
    }}

