package PiBuddy.SpringBootBackend.Repository;

import PiBuddy.SpringBootBackend.Entity.Groupe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupeRepository extends JpaRepository<Groupe,Long> {
}
