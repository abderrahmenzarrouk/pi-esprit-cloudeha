package com.example.pi_projet.repositories;

import com.example.pi_projet.entities.ReservationEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IReservationEventRepository extends JpaRepository<ReservationEvent,Long> {
}
