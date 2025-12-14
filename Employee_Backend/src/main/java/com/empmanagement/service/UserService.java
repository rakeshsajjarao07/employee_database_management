package com.empmanagement.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.empmanagement.entity.User;
import com.empmanagement.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileStorageService fileStorageService;

    /* =========================================================
       SIGN UP  (NO employee table check)
    ========================================================= */
    public User signUp(User user) throws Exception {

        // Validate username
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new RuntimeException("Username is required");
        }
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        // Validate email
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new RuntimeException("Email is required");
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        // Validate employee ID
        if (user.getEmployeeId() == null || user.getEmployeeId().trim().isEmpty()) {
            throw new RuntimeException("Employee ID is required");
        }
        if (userRepository.findByEmployeeId(user.getEmployeeId()).isPresent()) {
            throw new RuntimeException("User already exists for this Employee ID");
        }

        // Validate password
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            throw new RuntimeException("Password is required");
        }

        // Validate department
        if (user.getDepartment() == null || user.getDepartment().trim().isEmpty()) {
            throw new RuntimeException("Department is required");
        }

        // Validate Base64 image
        if (user.getImageBase64() == null || user.getImageBase64().trim().isEmpty()) {
            throw new RuntimeException("Profile image is required");
        }
        
        

        /* ----------------------------------------------------
            SAVE USER PROFILE IMAGE
            (Uses FileStorageService method)
        ---------------------------------------------------- */
        String savedFileName = fileStorageService.saveBase64Image(user.getImageBase64());
        user.setImage(savedFileName);

        // Clear transient field
        user.setImageBase64(null);
        user.setCreatedAt(LocalDateTime.now());


        return userRepository.save(user);
    }

    /* =========================================================
       SIGN IN
    ========================================================= */
    public User signIn(String username, String employeeId, String password) {

        Optional<User> optional = userRepository.findByUsername(username);
        if (optional.isEmpty()) {
            throw new RuntimeException("Invalid username or password");
        }

        User user = optional.get();

        if (!user.getEmployeeId().equals(employeeId)) {
            throw new RuntimeException("Employee ID does not match");
        }

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Incorrect password");
        }

        // Update last login timestamp
        user.setLastLoginAt(java.time.LocalDateTime.now());
        userRepository.save(user);

        return user;
    }
}
