package com.example.pi_projet.repositories;


import com.example.pi_projet.entities.Commentaire;
import com.example.pi_projet.entities.items;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentaireRepository extends JpaRepository<Commentaire,Long> {
    List <Commentaire> findByItem(items items);
}
