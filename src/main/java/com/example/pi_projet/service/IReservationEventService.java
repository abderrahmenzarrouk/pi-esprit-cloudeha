package com.example.pi_projet.service;

import com.example.pi_projet.entities.ReservationEvent;

import java.util.List;

public interface IReservationEventService {
    List<ReservationEvent> getAllRes();
    ReservationEvent retrieveRes(long id);

    ReservationEvent addRes(Long evenementId, Long quantity,long iduser);

    ReservationEvent updateRes(ReservationEvent reservationEvent);
    void deleteRes(long id);
    List<ReservationEvent> getReservationsForEvent(long eventId); // Correction ici : changer le nom de la variable d'ID

}
