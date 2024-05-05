package com.example.pi_projet.controllers;

import com.example.pi_projet.entities.User;
import com.example.pi_projet.repositories.userRepository;
import com.example.pi_projet.service.AuthenticationService;
import com.example.pi_projet.service.userService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthenticationController {
    private final AuthenticationService service;
    @Autowired
    private com.example.pi_projet.service.userService userService;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }
    @PostMapping("/forgot-password")
    public User getreclamationbyuser(@RequestBody Map<String, String> requestBody){
        String useremail = requestBody.get("email");
        return userService.forgotpassword(useremail);}

}
