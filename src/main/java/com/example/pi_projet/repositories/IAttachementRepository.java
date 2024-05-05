package com.example.pi_projet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.pi_projet.entities.Attachement;

public interface IAttachementRepository extends JpaRepository<Attachement,Long> {
}
