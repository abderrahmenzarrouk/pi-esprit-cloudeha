package com.example.pi_projet.service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.example.pi_projet.entities.Reservation;
import com.example.pi_projet.entities.ressources;

public interface IReservationService {
    public Reservation addReservation(long idClasse, long idGroupe, LocalDateTime debutReservation, LocalDateTime finReservation);

    public Reservation getReservationById(Long idReservation);

    public List<Reservation> getAllReservations();

    public Reservation updateReservation(Reservation reservation);

    public void deleteReservationById(Long idReservation);

//    public List<Reservation> getReservationsValidees();

    public Map<DayOfWeek, Integer> getStatsJoursSemaine();
    public Map<String, Integer> getStatsHeures();
}

