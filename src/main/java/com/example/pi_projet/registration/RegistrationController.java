package com.example.pi_projet.registration;


import com.example.pi_projet.entities.Roles;
import com.example.pi_projet.repositories.roleRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "api/v1/auth/registration")
@AllArgsConstructor
@CrossOrigin("*")
public class RegistrationController {

    private RegistrationService registrationService;
    @Autowired
    private roleRepository roleR;
    @PostMapping
    public String register(@RequestParam("prenom") String prenom,
                           @RequestParam("nom") String nom,
                           @RequestParam("email") String email,
                           @RequestParam("MDP") String MDP,
                           @RequestParam("age") String age,
                           @RequestParam("tel") String tel,
                           @RequestParam("role") int  roleId,
                           @RequestParam("specialite") String  specialite,
                           @RequestParam("file") MultipartFile file){
        Roles role = roleR.findById(roleId).get();
        RegistrationRequest request = new RegistrationRequest(prenom, nom, email, MDP, age, tel, role,specialite);
        System.out.println("request");
        System.out.println(request);
        return registrationService.register(request , file);
    }
    @PostMapping(path = "/tuteur")
    public String registertuteur(@RequestParam("prenom") String prenom,
                                 @RequestParam("nom") String nom,
                                 @RequestParam("email") String email,
                                 @RequestParam("MDP") String MDP,
                                 @RequestParam("age") String age,
                                 @RequestParam("tel") String tel,
                                 @RequestParam("role") int  roleId,
                                 @RequestParam("specialite") String  specialite,
                                 @RequestParam("file") MultipartFile file){
        try {
            Roles role = roleR.findById(roleId).get();
            RegistrationRequest request = new RegistrationRequest(prenom, nom, email, MDP, age, tel, role, specialite);
            return registrationService.registertuteur(request, file);
        }
        catch (Exception e) {
            return "Une erreur s'est produite : " + e.getMessage();
        }
    }
    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }




}
