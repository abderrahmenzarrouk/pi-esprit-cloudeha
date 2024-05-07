package com.esprit.piproject.services;

import com.esprit.piproject.entities.ReservationEvent;

import java.util.List;

public interface IReservationEventService {
    List<ReservationEvent> getAllRes();
    ReservationEvent retrieveRes(long id);
    ReservationEvent addRes(ReservationEvent reservationEvent );
    ReservationEvent updateRes(ReservationEvent reservationEvent);
    void deleteRes(long id);
}
