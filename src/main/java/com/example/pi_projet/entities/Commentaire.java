package com.example.pi_projet.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
public class Commentaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String contenu;
    private String nom;

    public Commentaire() {

    }
    @ManyToOne
    @JoinColumn(name = "item_id")
    private items item;

}
