package com.esprit.piproject.repositories;

import com.esprit.piproject.entities.Publication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IPublicationRepository extends JpaRepository<Publication, Long> {
    List<Publication>findAllByNomContaining(String nom);
}
