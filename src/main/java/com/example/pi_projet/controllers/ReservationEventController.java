package com.example.pi_projet.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.pi_projet.entities.ReservationEvent;
import com.example.pi_projet.service.IReservationEventService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("resEvents")
@CrossOrigin("*")
public class ReservationEventController {
    private IReservationEventService iReservationEventService;
    @GetMapping("/")
    public ResponseEntity<List<ReservationEvent>> findAllRes(){
        List<ReservationEvent> res=iReservationEventService.getAllRes();

        if(!res.isEmpty())
            return new ResponseEntity<>(res, HttpStatus.OK);
        return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
    }
    @GetMapping("/findOne/{id}")
    public  ResponseEntity<ReservationEvent> findRes(@PathVariable("id") long id){
        ReservationEvent res = iReservationEventService.retrieveRes(id);
        if(res!=null)
            return new ResponseEntity<>(res, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping("/")
    public ResponseEntity<ReservationEvent> saveRes(@RequestBody Long evenementId){
        ReservationEvent reservationEvent = iReservationEventService.addRes(evenementId);
        if (reservationEvent != null) {
            return new ResponseEntity<>(reservationEvent, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/")
    public ResponseEntity<ReservationEvent> modifyRes(@RequestBody ReservationEvent res){
        ReservationEvent reservationEvent= iReservationEventService.updateRes(res);
        if(reservationEvent!=null)
            return new ResponseEntity<>(reservationEvent, HttpStatus.OK);
        return new ResponseEntity<>(reservationEvent, HttpStatus.NOT_FOUND);

    }
    @DeleteMapping("/{id}")
    public void removeRes(@PathVariable("id") long id){
        iReservationEventService.deleteRes(id);
    }
    @GetMapping("/event/{eventId}") // Correction ici : utiliser le bon nom de variable d'ID
    public ResponseEntity<List<ReservationEvent>> findReservationsForEvent(@PathVariable("eventId") long eventId) {
        List<ReservationEvent> reservations = iReservationEventService.getReservationsForEvent(eventId);
        if (!reservations.isEmpty()) {
            return new ResponseEntity<>(reservations, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}


