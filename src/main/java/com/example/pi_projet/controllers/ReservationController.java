package com.example.pi_projet.controllers;


import com.example.pi_projet.entities.Reservation;
import com.example.pi_projet.service.IReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private IReservationService reservationService;

    @PostMapping("/addReservation/{idClasse}/{idGroupe}")
    public Reservation addReservation(@PathVariable long idClasse,
                                      @PathVariable long idGroupe,
                                      @RequestBody Reservation reservation) {
        return reservationService.addReservation(idClasse, idGroupe, reservation.getDebutReservation(), reservation.getFinReservation());
    }


    @GetMapping("/{idReservation}")
    public Reservation getReservationById(@PathVariable Long idReservation) {
        return reservationService.getReservationById(idReservation);
    }


    @GetMapping("/getAllReservation")
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @PutMapping
    public Reservation updateReservation(@RequestBody Reservation reservation) {
        return reservationService.updateReservation(reservation);
    }

    @DeleteMapping("/DeleteReservation/{id}")
    public void deleteReservation(@PathVariable("id") long id) {
        reservationService.deleteReservationById(id);
    }

//    @GetMapping("/getValidReservations")
//    public List<Reservation> getValidReservations() {
//        return reservationService.getReservationsValidees();
//    }

    @GetMapping("/statistiques")
    public ResponseEntity<Map<String, Object>> getStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("statsParHeure", reservationService.getStatsHeures());
        stats.put("statsParJour", reservationService.getStatsJoursSemaine());
        return ResponseEntity.ok(stats);
    }

}
