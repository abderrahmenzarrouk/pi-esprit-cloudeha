package com.example.pi_projet.controllers;

import com.example.pi_projet.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/demo-controller")
public class DemoController {
    private userService userService;
    @Autowired
    public DemoController(userService userService) {
        this.userService = userService;
    }
    @GetMapping
    public ResponseEntity<String> sayHello() {


        return ResponseEntity.ok("Hello from secured endpoint");
    }
}
