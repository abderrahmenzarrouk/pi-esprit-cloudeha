package com.example.pi_projet.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "Reclamation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Reclamation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @ManyToOne
    private User user;
    @Column(name = "Description")
    private String description;
    @Column(name = "Tuteur")
    private String Tuteurchoisit;
    @Column(name = "Etat")
    private int etatreclamation;
    @Enumerated(EnumType.STRING)
    private TypeReclamtion typeReclamtion;
    @Temporal(TemporalType.DATE)
    private Date datereclamation ;
}
