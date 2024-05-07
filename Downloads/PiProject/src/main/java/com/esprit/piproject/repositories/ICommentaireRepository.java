package com.esprit.piproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.esprit.piproject.entities.Commentaire;

import java.util.List;

public interface ICommentaireRepository extends JpaRepository<Commentaire, Long> {


    List<Commentaire> findByPublication_Id(long publication_id);

}
