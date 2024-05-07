package com.esprit.piproject.repositories;

import com.esprit.piproject.entities.ReservationEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IReservationEventRepository extends JpaRepository<ReservationEvent,Long> {
}
