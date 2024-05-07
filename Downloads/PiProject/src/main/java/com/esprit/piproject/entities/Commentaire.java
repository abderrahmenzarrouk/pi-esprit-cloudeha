package com.esprit.piproject.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level=AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
public class Commentaire implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String contenu;

    int likes;

    Date dateCommentaire;

    private String postedBy;

    @ManyToOne
    @JoinColumn(name = "publication_id", nullable = false)
    Publication publication;
}
