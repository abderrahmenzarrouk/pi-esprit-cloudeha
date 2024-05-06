package com.example.pi_projet.controllers;

import com.example.pi_projet.entities.Reclamation;
import com.example.pi_projet.entities.Roles;
import com.example.pi_projet.entities.User;
import com.example.pi_projet.registration.RegistrationRequest;
import com.example.pi_projet.repositories.userRepository;
import com.example.pi_projet.service.userService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
@CrossOrigin("*")
public class userController {
    @Autowired
    private userRepository userRepository;
    @Autowired
    private userService userService;

    @GetMapping("/all")
    public List<User> getALLUsers(){
        return userRepository.findAll();
    }
    @GetMapping("/ByRole/{id}")
    public List<User> getALLUsersByRole(@RequestParam("id") int  roleId){
        return userRepository.findByUserRoleId(roleId);
    }
    @PostMapping("/updateuser")
    public User updateuser(@RequestParam(value = "prenom", required = false) String prenom,
                           @RequestParam(value = "nom", required = false) String nom,
                           @RequestParam(value = "email", required = false) String email,
                           @RequestParam(value = "age", required = false) String age,
                           @RequestParam(value = "tel", required = false) String tel,
                           @RequestParam("id") int id,
                           @RequestParam(value = "file", required = false) MultipartFile file){
        User u = userRepository.findById(id);
        if (prenom != null) {
            u.setPrenom(prenom);
        }
        if (nom != null) {
            u.setNom(nom);
        }
        if (email != null) {
            u.setEmail(email);
        }
        if (age != null) {
            u.setAge(age);
        }
        if (tel != null) {
            u.setTel(tel);
        }
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        System.out.println(fileName);

        if(fileName.contains(".."))
        {
            System.out.println("le fichier n'est pas valide");
        }
        try {
            u.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        u.setUserRole(u.getUserRole());
        u.setTypespecialite(u.getTypespecialite());
        u.setMdpoubliée(0);
        u.setDerniercnx(LocalDate.now());
        u.setDateregistration(u.getDateregistration());
        userRepository.save(u);
        return u;
    }

    @PostMapping("/changer-mdp")
    public User changermdp(@RequestBody Map<String, String> requestBody
                            ){
        return userService.changepassword(requestBody.get("email"),requestBody.get("newmdp"),requestBody.get("oldmdp"));

    }

/*
    @GetMapping("/acheter/{id}/{nbr}/{iditem}")
    public void acheteritem(@PathVariable int  id,@PathVariable int  nbr,@PathVariable int  iditem){
        userService.acheteritem(nbr,id,iditem);
    }
*/

}
