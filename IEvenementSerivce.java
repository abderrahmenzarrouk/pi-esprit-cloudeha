package com.example.pi_projet.services;

import com.example.pi_projet.entities.Evenement;

import java.util.List;

public interface IEvenementSerivce {
    List<Evenement> getAllEvents();
    Evenement retrieveEvent(long id);
    Evenement addEvent(Evenement event);
    Evenement updateEvent(Evenement event);
    void deleteEvent(long id);
}
