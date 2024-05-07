package com.esprit.piproject.controllers;

import com.esprit.piproject.services.IEvenementSerivce;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.esprit.piproject.entities.Evenement;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("events")

public class EvenementController {
    private IEvenementSerivce iEvenementSerivce;
    @GetMapping("/")
    public ResponseEntity<List<Evenement>> findAllEvents(){
        List<Evenement> events=iEvenementSerivce.getAllEvents();

        if(!events.isEmpty())
            return new ResponseEntity<>(events, HttpStatus.OK);
        return new ResponseEntity<>(events, HttpStatus.NOT_FOUND);
    }
    @GetMapping("/findOne/{id}")
    public  ResponseEntity<Evenement> findEvent(@PathVariable("id") long id){
        Evenement event = iEvenementSerivce.retrieveEvent(id);
        if(event!=null)
            return new ResponseEntity<>(event, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping("/")
    public ResponseEntity<Evenement> saveEvent(@RequestBody Evenement event){
        Evenement evenement= iEvenementSerivce.addEvent(event);
        if(evenement!=null)
            return new ResponseEntity<>(evenement, HttpStatus.CREATED);
        return new ResponseEntity<>(evenement, HttpStatus.NOT_FOUND);

    }
    @PutMapping("/")
    public ResponseEntity<Evenement> modifyEvent(@RequestBody Evenement event){
        Evenement evenement= iEvenementSerivce.updateEvent(event);
        if(evenement!=null)
            return new ResponseEntity<>(evenement, HttpStatus.OK);
        return new ResponseEntity<>(evenement, HttpStatus.NOT_FOUND);

    }
    @DeleteMapping("/{id}")
    public void removeEvent(@PathVariable("id") long id){
        iEvenementSerivce.deleteEvent(id);
    }
}
