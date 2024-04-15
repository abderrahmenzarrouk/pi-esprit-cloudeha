package com.example.pi_projet.registration;

import com.example.pi_projet.entities.Role;
import com.example.pi_projet.entities.Roles;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {
    private final String prenom;
    private final String nom;
    private final String email;
    private final String MDP;
    private final String age;
    private final String tel;
    private final Roles role;

}
