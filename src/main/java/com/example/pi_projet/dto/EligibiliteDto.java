package com.example.pi_projet.dto;

import com.example.pi_projet.entities.Groupe;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class EligibiliteDto {
    private long note_collectif_moyenne;
    private int score_vote;
    private String Lien_Youtube;
    Groupe groupe;
}
