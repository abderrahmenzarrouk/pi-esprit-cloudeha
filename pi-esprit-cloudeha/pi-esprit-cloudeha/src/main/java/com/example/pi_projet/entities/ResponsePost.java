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
@AllArgsConstructor
@NoArgsConstructor
public class ResponsePost implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idResponse")
    private Long idResponse;
    private String nom;
    private  String prenom;
    private String contenu;


    @ManyToOne
    @JsonIgnore
    private User user_ResponsePost;

    @ManyToOne
    @JsonIgnore
    private Post post;
}
