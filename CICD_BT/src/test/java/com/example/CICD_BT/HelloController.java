package com.example.CICD_BT;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/")
    public String hello() {
        return "Spring Boot chạy trên localhost:9090!";
    }
}
