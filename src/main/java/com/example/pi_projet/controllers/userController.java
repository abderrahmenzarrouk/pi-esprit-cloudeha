package com.example.pi_projet.controllers;

import com.example.pi_projet.entities.Roles;
import com.example.pi_projet.entities.User;
import com.example.pi_projet.repositories.userRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
@CrossOrigin("*")
public class userController {
    @Autowired
    private userRepository userRepository;

    @GetMapping("/all")
    public List<User> getALLUsers(){
        return userRepository.findAll();
    }
    @GetMapping("/by-role")
    public List<User> getUsersByRoleId(@RequestParam int roleId) {
        return userRepository.findByUserRole_Id(roleId);
    }
}
