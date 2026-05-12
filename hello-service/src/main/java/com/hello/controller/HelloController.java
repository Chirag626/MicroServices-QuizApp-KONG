package com.hello.controller;

import com.hello.dto.HelloResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    // Main test API
    @GetMapping("/hello")
    public HelloResponse sayHello() {
        return new HelloResponse(
                "Hello from Java 21 Spring Boot service",
                "hello-service",
                "UP"
        );
    }

    // Simple manual health API for Kong testing
    @GetMapping("/health")
    public HelloResponse healthCheck() {
        return new HelloResponse(
                "Hello Service is running",
                "hello-service",
                "UP"
        );
    }
}