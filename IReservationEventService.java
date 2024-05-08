package com.example.pi_projet.services;

import com.example.pi_projet.entities.ReservationEvent;

import java.util.List;

public interface IReservationEventService {
    List<ReservationEvent> getAllRes();
    ReservationEvent retrieveRes(long id);
    ReservationEvent addRes(ReservationEvent reservationEvent );
    ReservationEvent updateRes(ReservationEvent reservationEvent);
    void deleteRes(long id);
}
