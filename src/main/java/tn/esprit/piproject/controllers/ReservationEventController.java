package tn.esprit.piproject.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.piproject.entities.Evenement;
import tn.esprit.piproject.entities.ReservationEvent;
import tn.esprit.piproject.services.IReservationEventService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("resEvents")
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
    public ResponseEntity<ReservationEvent> saveRes(@RequestBody ReservationEvent res){
        ReservationEvent reservationEvent= iReservationEventService.addRes(res);
        if(reservationEvent!=null)
            return new ResponseEntity<>(reservationEvent, HttpStatus.CREATED);
        return new ResponseEntity<>(reservationEvent, HttpStatus.NOT_FOUND);

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
}
