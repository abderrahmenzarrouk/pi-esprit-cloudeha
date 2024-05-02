package com.example.shop.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
public class Items {
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

    public Items() {

    }
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    @JsonIgnore // Exclure les commentaires de la sérialisation JSON

    private List<Commentaire> commentaires = new ArrayList<>();


}
