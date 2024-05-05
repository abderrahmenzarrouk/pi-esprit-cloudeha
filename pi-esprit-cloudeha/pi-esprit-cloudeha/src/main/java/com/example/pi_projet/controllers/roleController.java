package com.example.pi_projet.controllers;

import com.example.pi_projet.entities.Roles;
import com.example.pi_projet.repositories.roleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/roles")
public class roleController {
    @Autowired
    private roleRepository roleR;

    @GetMapping("/all")
    public List<Roles> getALLroles(){
        return roleR.findAll();
    }

    @PostMapping("/role")
    public Roles adduser(@RequestBody Roles r ){
        return  roleR.save(r);
    }
}
