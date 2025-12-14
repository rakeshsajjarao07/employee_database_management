package com.empmanagement.controller;

import com.empmanagement.entity.User;
import com.empmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody User user) {
        try {
            if (user.getImageBase64() == null || user.getImageBase64().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Base64 image is required"));
            }

            User savedUser = userService.signUp(user);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "User registered successfully");
            response.put("user", savedUser);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody Map<String, String> credentials) {
        try {
            String username = credentials.get("username");
            String employeeId = credentials.get("employeeId");
            String password = credentials.get("password");

            User user = userService.signIn(username, employeeId, password);

            return ResponseEntity.ok(user);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}
