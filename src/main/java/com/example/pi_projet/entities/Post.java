package com.example.pi_projet.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Post implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPost")
    private Long idPost;
    private String nom;
    private String prenom;
    private String Contenu;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private User user_post;

    @ManyToOne
    @JsonIgnore
    private Groupe groupePosts;

    @OneToMany(mappedBy = "post")
    @JsonIgnore
    private Set<ResponsePost> responsePostSet;

}