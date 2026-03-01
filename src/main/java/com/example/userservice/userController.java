package com.example.userservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class userController {

    @GetMapping("/greetings")
    public String greetings() {
        return "Welcome to my World , you are now using Kubernetes";
    }
}
