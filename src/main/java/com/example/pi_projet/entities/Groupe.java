package com.example.pi_projet.entities;

import com.example.pi_projet.dto.GroupeDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.Year;
import java.util.Set;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Groupe implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idGroupe")
    private Long idGroupe;
    private String Nom_Groupe;

    //esmou w lakabou
    private int Nom_Tuteur;
    private float Total_Score;

    //maabi wla la
    private String Statut;

    //etat bech nehsbou beha nombre mtaaa lusers fel groupe
    private int Etat;

    private String Sujet ;


    private Year annee;

    @OneToMany(mappedBy = "groupeInvitation")
    @JsonIgnore
    private Set<Invitation> invitationsGroupe;

    @Enumerated(EnumType.STRING)
    private Option option;


    @ManyToMany(mappedBy = "groupeSet")
    @JsonIgnore
    private Set<User> userSet;


    @OneToMany(mappedBy = "groupePosts")
    @JsonIgnore
    private Set<Post> posts;
    @OneToOne(mappedBy = "groupe")
    Eligibilite eligibilite;
public Groupe toEntity(GroupeDto groupeDto){
    return Groupe.builder().Nom_Groupe(groupeDto.getNom_Groupe()).Sujet(groupeDto.getSujet()).build();
}

}
