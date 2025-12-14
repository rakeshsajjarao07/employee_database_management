package com.empmanagement.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class WelcomeController {

    @GetMapping("/")
    public String welcome() {
        return "Employee Management Backend is Running Successfully!";
    }
}
