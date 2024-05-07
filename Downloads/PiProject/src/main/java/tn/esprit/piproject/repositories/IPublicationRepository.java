package tn.esprit.piproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.piproject.entities.Publication;

import java.util.List;

public interface IPublicationRepository extends JpaRepository<Publication, Long> {
    List<Publication>findAllByNomContaining(String nom);
}
