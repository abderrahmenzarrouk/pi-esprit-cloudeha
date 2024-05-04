package com.example.pi_projet.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RendezVous implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idRdv")
    private Long idRdv ;
    private String Duree;
    private LocalDate date;
    private String Heure;
    private String Description;
    private float Points ;
    private String salle;
    private int nombre;
    private String etat;
    private String lienMeet;


    @ManyToOne
    @JsonIgnore
    private Groupe groupe;

}
