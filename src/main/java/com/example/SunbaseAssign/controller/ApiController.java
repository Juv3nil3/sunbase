package com.example.SunbaseAssign.controller;

import com.example.SunbaseAssign.dto.request.LoginRequest;
import com.example.SunbaseAssign.service.ApiService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class ApiController {
    private final ApiService apiService;

    public ApiController(ApiService apiService) {
        this.apiService = apiService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> authenticate(@RequestBody LoginRequest loginRequest) {
        try {
            String loginId = loginRequest.getLogin_id();
            String password = loginRequest.getPassword();
            String token = apiService.authenticateUser(loginId, password);
            return ResponseEntity.ok(token);
        }  catch (Exception e) {
            // Other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Here i am");
        }
    }
}
