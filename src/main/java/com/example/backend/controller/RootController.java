package com.example.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // Use @Controller instead of @RestController to serve views
public class RootController {

    @GetMapping("/")
    public String home() {
        return "index"; // Maps to index.html in /static/
    }
}