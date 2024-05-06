package com.example.pi_projet.entities;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Eligibilite")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Eligibilite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_eligibilite;
    private long note_collectif_moyenne;
    @Column(name = "score_vote", columnDefinition = "TINYINT")
    private int score_vote;
    private String Lien_Youtube;

}