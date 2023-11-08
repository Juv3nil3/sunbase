package com.example.SunbaseAssign.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class ApiService {
    @Autowired
    private RestTemplate restTemplate;

    private String accessToken;

    public String authenticateUser(String loginId, String password) throws AuthenticationException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("login_id", loginId);
        requestBody.put("password", password);

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("https://qa2.sunbasedata.com/sunbase/portal/api/assignment_auth.jsp" , requestEntity, String.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            String jsonResponse = responseEntity.getBody();
            this.accessToken = extractAccessToken(jsonResponse);
            return this.accessToken;
        } else {
            throw new RuntimeException("Authentication failed with status code: " + responseEntity.getStatusCodeValue());
        }
    }
    private String extractAccessToken(String jsonResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonResponse);
            return jsonNode.get("access_token").asText();
        } catch (Exception e) {
            throw new RuntimeException("Failed to extract access token from JSON response: " + e.getMessage());
        }
    }

    public String getAccessToken() {
        return this.accessToken; // Method to retrieve the saved access token
    }

}
