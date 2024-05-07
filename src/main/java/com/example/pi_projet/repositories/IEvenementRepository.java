package com.example.pi_projet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.pi_projet.entities.Evenement;

public interface IEvenementRepository extends JpaRepository<Evenement,Long> {
}
