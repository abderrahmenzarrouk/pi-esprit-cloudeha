package com.example.pi_projet.repositories;


import com.example.pi_projet.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    @Query("SELECT r FROM Reservation r WHERE r.finReservation < ?1")
    List<Reservation> findByFinReservationBefore(LocalDateTime now);
}
