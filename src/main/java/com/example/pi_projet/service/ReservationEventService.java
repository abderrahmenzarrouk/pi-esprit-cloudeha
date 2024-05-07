package com.example.pi_projet.service;

import com.example.pi_projet.entities.User;
import com.example.pi_projet.repositories.userRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.pi_projet.entities.Evenement;
import com.example.pi_projet.entities.ReservationEvent;
import com.example.pi_projet.repositories.IEvenementRepository;
import com.example.pi_projet.repositories.IReservationEventRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ReservationEventService implements IReservationEventService{
    private IReservationEventRepository iReservationEventRepository;
    private IEvenementRepository iEvenementRepository;
    @Autowired
    private userService userService;
    private final com.example.pi_projet.repositories.userRepository userRepository;

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
    public ReservationEvent addRes(Long evenementId, Long quantity,long iduser) {
        Evenement event = iEvenementRepository.findById(evenementId).orElse(null);
        User u = userRepository.findById(iduser);

        if (event != null && event.getPlacesRestants() > 0) {
            event.setPlacesRestants(event.getPlacesRestants() - quantity);
            iEvenementRepository.save(event);

            ReservationEvent reservationEvent = new ReservationEvent();
            reservationEvent.setEvenement(event);
            reservationEvent.setQuantity(quantity);
            reservationEvent.setUser(u);

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
