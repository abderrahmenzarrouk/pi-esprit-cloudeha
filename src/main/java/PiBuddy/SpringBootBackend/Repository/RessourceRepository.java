package PiBuddy.SpringBootBackend.Repository;

import PiBuddy.SpringBootBackend.Entity.Classe;
import PiBuddy.SpringBootBackend.Entity.Reservation;
import PiBuddy.SpringBootBackend.Entity.ressources;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface RessourceRepository extends JpaRepository<ressources,Long> {
    List<ressources> findAllByClassesIs(Classe c );

}
