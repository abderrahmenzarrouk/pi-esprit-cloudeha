package com.esprit.piproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.esprit.piproject.entities.Evenement;

public interface IEvenementRepository extends JpaRepository<Evenement,Long> {
}
