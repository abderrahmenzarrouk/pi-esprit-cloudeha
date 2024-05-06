package com.example.pi_projet.entities;
import com.example.pi_projet.dto.EligibiliteDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Eligibilite")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class Eligibilite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_eligibilite;
    private long note_collectif_moyenne;
    @Column(name = "score_vote", columnDefinition = "TINYINT")
    private int score_vote;
    private String Lien_Youtube;
    @OneToOne
    Groupe groupe;
    public Eligibilite toEntity(EligibiliteDto eligibiliteDto){
        return Eligibilite.builder().groupe(eligibiliteDto.getGroupe()).Lien_Youtube(eligibiliteDto.getLien_Youtube()).note_collectif_moyenne(eligibiliteDto.getNote_collectif_moyenne()).score_vote(eligibiliteDto.getScore_vote()).build();
    }

}