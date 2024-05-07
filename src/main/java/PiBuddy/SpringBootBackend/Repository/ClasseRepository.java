package PiBuddy.SpringBootBackend.Repository;

import PiBuddy.SpringBootBackend.Entity.Classe;
import PiBuddy.SpringBootBackend.Entity.ressources;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClasseRepository extends JpaRepository<Classe,Long> {



    @Query("SELECT DISTINCT c FROM Classe c left JOIN FETCH c.ressources")
    List<Classe> findAllWithRessources();


}
