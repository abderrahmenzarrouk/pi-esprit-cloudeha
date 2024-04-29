package tn.esprit.piproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.piproject.entities.ReservationEvent;

public interface IReservationEventRepository extends JpaRepository<ReservationEvent,Long> {
}
