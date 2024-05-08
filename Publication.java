package com.example.pi_projet.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level=AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
public class Publication implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    private String nom ;

    String contenu;

    private String img;

    private String postedby;

    Date datePublication;

    int likes;

    private int viewCount;

    @ElementCollection
    private List<String> tags;


    @OneToMany(mappedBy = "publication", cascade = CascadeType.ALL)
        @JsonIgnore
    List<CommentairePub> commentairePubs;

    //@Lob
    //private byte[] imageBytes;
}
