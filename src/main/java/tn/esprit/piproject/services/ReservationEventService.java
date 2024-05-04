package tn.esprit.piproject.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.piproject.entities.Evenement;
import tn.esprit.piproject.entities.ReservationEvent;
import tn.esprit.piproject.repositories.IEvenementRepository;
import tn.esprit.piproject.repositories.IReservationEventRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ReservationEventService implements IReservationEventService{
    private IReservationEventRepository iReservationEventRepository;
    private IEvenementRepository iEvenementRepository;
    @Override
    public List<ReservationEvent> getAllRes() {
        return iReservationEventRepository.findAll();
    }

    @Override
    public ReservationEvent retrieveRes(long id) {
        ReservationEvent reservationEvent=iReservationEventRepository.findById(id).get();
        return reservationEvent;
    }

    @Override
    public ReservationEvent addRes(Long evenementId) {
        Evenement event = iEvenementRepository.findById(evenementId).orElse(null);

        if (event != null && event.getPlacesRestants() > 0) {
            event.setPlacesRestants(event.getPlacesRestants() - 1);
            iEvenementRepository.save(event);

            ReservationEvent reservationEvent = new ReservationEvent();
            reservationEvent.setEvenement(event);

            return iReservationEventRepository.save(reservationEvent);
        } else {
            return null;
        }
    }


    @Override
    public ReservationEvent updateRes(ReservationEvent reservationEvent) {
        return iReservationEventRepository.save(reservationEvent);
    }

    @Override
    public void deleteRes(long id) {
      iReservationEventRepository.deleteById(id);
    }
    @Override
    public List<ReservationEvent> getReservationsForEvent(long eventId) {
        return iReservationEventRepository.findByEvenementId(eventId); // Correction ici : utiliser le bon nom de variable d'ID
    }
}
