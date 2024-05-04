package tn.esprit.piproject.services;

import tn.esprit.piproject.entities.Evenement;
import tn.esprit.piproject.entities.ReservationEvent;

import java.util.List;

public interface IReservationEventService {
    List<ReservationEvent> getAllRes();
    ReservationEvent retrieveRes(long id);
    public ReservationEvent addRes(Long evenementId) ;

        ReservationEvent updateRes(ReservationEvent reservationEvent);
    void deleteRes(long id);
    List<ReservationEvent> getReservationsForEvent(long eventId); // Correction ici : changer le nom de la variable d'ID

}
