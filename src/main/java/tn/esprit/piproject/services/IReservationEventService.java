package tn.esprit.piproject.services;

import tn.esprit.piproject.entities.Evenement;
import tn.esprit.piproject.entities.ReservationEvent;

import java.util.List;

public interface IReservationEventService {
    List<ReservationEvent> getAllRes();
    ReservationEvent retrieveRes(long id);
    ReservationEvent addRes(ReservationEvent reservationEvent );
    ReservationEvent updateRes(ReservationEvent reservationEvent);
    void deleteRes(long id);
}
