package com.example.pi_projet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.pi_projet.entities.RendezVous;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RendezVousRepository extends JpaRepository<RendezVous,Long> {

    List<RendezVous> findRendezVousByDate (LocalDate date);
    List<RendezVous> findRendezVousByDateBetween (LocalDate dateDeb , LocalDate dateFin);

    List<RendezVous> findRendezVousByGroupe_IdGroupe (Long idGroupe);

    RendezVous findByIdRdv(Long idRdv);

    @Query("SELECT r.Heure FROM RendezVous r WHERE r.date = :date AND r.groupe.id = :groupId")
    List<String> findHeureByDateAndGroupeIdGroupe(@Param("date") LocalDate date, @Param("groupId") Long groupId);
}
