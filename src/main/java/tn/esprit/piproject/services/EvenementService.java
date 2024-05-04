package tn.esprit.piproject.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.piproject.entities.Evenement;
import tn.esprit.piproject.entities.ReservationEvent;
import tn.esprit.piproject.repositories.IEvenementRepository;
import tn.esprit.piproject.repositories.IReservationEventRepository;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class EvenementService implements IEvenementSerivce {
    private IEvenementRepository iEvenementRepository;
    private IReservationEventRepository iReservationEventRepository;

    @Override
    public List<Evenement> getAllEvents() {
        return iEvenementRepository.findAll();
    }

    @Override
    public Evenement retrieveEvent(long id) {
        Evenement event=iEvenementRepository.findById(id).get();
        return event;
    }

    @Override
    public Evenement addEvent(Evenement event) {
        return iEvenementRepository.save(event);
    }

    @Override
    public Evenement updateEvent(Evenement event) {
        return iEvenementRepository.save(event);
    }

    @Override
    public void deleteEvent(long id) {
        // Récupérer l'événement à supprimer
        Evenement event = iEvenementRepository.findById(id).orElse(null);
        if (event != null) {
            // Récupérer l'ID de l'événement
            Long evenementId = event.getId();

            // Supprimer toutes les réservations associées à cet événement
            iReservationEventRepository.deleteByEvenementId(evenementId);

            // Supprimer l'événement lui-même
            iEvenementRepository.deleteById(id);
        } else {
            System.out.println("L'événement avec l'ID " + id + " n'a pas été trouvé.");
        }
    }




}