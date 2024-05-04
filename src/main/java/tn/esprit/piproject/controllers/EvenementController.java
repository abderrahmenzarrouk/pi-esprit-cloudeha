package tn.esprit.piproject.controllers;

import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.piproject.entities.Evenement;
import tn.esprit.piproject.repositories.IReservationEventRepository;
import tn.esprit.piproject.services.IEvenementSerivce; // J'ai ajusté le nom du service

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("events")
@CrossOrigin("*")
public class EvenementController {
    private IEvenementSerivce iEvenementSerivce; // J'ai ajusté le nom du service
    private IReservationEventRepository reservationEventRepository;

    @GetMapping("/")
    public ResponseEntity<List<Evenement>> findAllEvents() {
        List<Evenement> events = iEvenementSerivce.getAllEvents();

        if (!events.isEmpty())
            return new ResponseEntity<>(events, HttpStatus.OK);
        return new ResponseEntity<>(events, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/findOne/{id}")
    public ResponseEntity<Evenement> findEvent(@PathVariable("id") long id) {
        Evenement event = iEvenementSerivce.retrieveEvent(id);
        if (event != null)
            return new ResponseEntity<>(event, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/")
    public ResponseEntity<Evenement> saveEvent(@RequestBody Evenement event) {
        // Enregistrer l'événement
        Evenement savedEvent = iEvenementSerivce.addEvent(event);

        // Retourner l'événement enregistré avec un statut HTTP 201 (Created)
        return new ResponseEntity<>(savedEvent, HttpStatus.CREATED);
    }

    @PutMapping("/")
    public ResponseEntity<Evenement> modifyEvent(@RequestBody Evenement event) {
        Evenement updatedEvent = iEvenementSerivce.updateEvent(event);
        if (updatedEvent != null)
            return new ResponseEntity<>(updatedEvent, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeEvent(@PathVariable("id") long id) {
        try {
            // Log avant la suppression des réservations
            System.out.println("Suppression des réservations liées à l'événement...");

            // Supprimer d'abord les réservations liées à cet événement
            reservationEventRepository.deleteByEvenementId(id);

            // Log après la suppression des réservations
            System.out.println("Réservations supprimées avec succès.");

            // Log avant la suppression de l'événement lui-même
            System.out.println("Suppression de l'événement lui-même...");

            // Ensuite, supprimer l'événement lui-même
            iEvenementSerivce.deleteEvent(id);

            // Log après la suppression de l'événement
            System.out.println("Événement supprimé avec succès.");

            return new ResponseEntity<>("Événement supprimé avec succès", HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            // Log en cas d'événement non trouvé
            System.out.println("Événement non trouvé.");

            return new ResponseEntity<>("Événement non trouvé", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // Log en cas d'erreur lors de la suppression
            System.out.println("Erreur lors de la suppression de l'événement : " + e.getMessage());

            return new ResponseEntity<>("Erreur lors de la suppression de l'événement", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
