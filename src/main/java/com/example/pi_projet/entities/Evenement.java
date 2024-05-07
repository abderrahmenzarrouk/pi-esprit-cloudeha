package com.example.pi_projet.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level=AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
public class Evenement implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    long id;
    String nom_event;
    LocalDateTime date_debut;
    LocalDateTime date_fin;
    long placesDispo;
    long placesRestants;
    @OneToMany(mappedBy = "evenement",cascade = CascadeType.ALL,orphanRemoval = true)
    Set<ReservationEvent> reservationEvents;
    private String image;

}
