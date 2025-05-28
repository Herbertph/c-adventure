package com.adventure.lessonservice.service;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

@Service
public class CodeExecutionService {

    private final RestTemplate restTemplate = new RestTemplate();

    public String executeCode(String language, String code, String input) {
        String pistonUrl = "https://emkc.org/api/v2/piston/execute";

        Map<String, Object> request = new HashMap<>();
        request.put("language", language);
        request.put("source", code);
        request.put("stdin", input);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(pistonUrl, entity, Map.class);
        Map<String, Object> body = response.getBody();

        if (body != null && body.containsKey("output")) {
            return body.get("output").toString();
        }

        return "Erro ao executar o código";
    }
}

