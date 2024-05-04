package tn.esprit.piproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.piproject.entities.Attachement;

public interface IAttachementRepository extends JpaRepository<Attachement,Long> {
}
