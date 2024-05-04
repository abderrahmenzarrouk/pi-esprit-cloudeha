package com.example.pi_projet.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Invitation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idInvitation")
    private Long idInvitation;
    private String nom;
    private String prenom;
    private Long UserID;
    @ManyToOne
    @JsonIgnore
    private Groupe groupeInvitation;



    @OneToOne
    @JsonIgnore
    private User userInvitation;


}
