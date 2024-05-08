package com.example.pi_projet.repositories;

import com.example.pi_projet.entities.Publication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IPublicationRepository extends JpaRepository<Publication, Long> {
    List<Publication>findAllByNomContaining(String nom);
}
