package tn.esprit.piproject.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.piproject.entities.ReservationEvent;

import java.util.List;

public interface IReservationEventRepository extends JpaRepository<ReservationEvent,Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM ReservationEvent re WHERE re.evenement.id = :evenementId")
    void deleteByEvenementId(@Param("evenementId") long evenementId);
    List<ReservationEvent> findByEvenementId(long id);







}
