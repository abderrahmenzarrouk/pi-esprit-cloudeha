package tn.esprit.piproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.piproject.entities.Evenement;

public interface IEvenementRepository extends JpaRepository<Evenement,Long> {
}
