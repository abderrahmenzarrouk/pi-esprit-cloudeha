package com.example.pi_projet.repositories;

import com.example.pi_projet.entities.Reclamation;
import com.example.pi_projet.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface reclamationRepository extends JpaRepository<Reclamation, Long> {
    List<Reclamation> findByUser_Id(int id);

}
