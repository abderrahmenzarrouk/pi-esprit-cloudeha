package com.example.pi_projet.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
public class items {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String description;
    private int nombreDePoints;
    @Column(columnDefinition = "MEDIUMBLOB")
    private String image;
    @Enumerated(EnumType.STRING)
    private TypeItem typeItem;
    @Enumerated(EnumType.STRING)
    @Column(length = 255)
    private Mode mode;
    @Column(columnDefinition = "MEDIUMBLOB")
    @Lob



    private byte[] documentation;
    private float note;



    public items() {

    }
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    @JsonIgnore // Exclure les commentaires de la sérialisation JSON

    private List<Commentaire> commentaires = new ArrayList<>();


}
