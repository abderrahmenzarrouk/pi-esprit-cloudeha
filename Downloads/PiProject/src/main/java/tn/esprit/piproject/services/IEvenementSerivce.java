package tn.esprit.piproject.services;

import tn.esprit.piproject.entities.Evenement;

import java.util.List;

public interface IEvenementSerivce {
    List<Evenement> getAllEvents();
    Evenement retrieveEvent(long id);
    Evenement addEvent(Evenement event);
    Evenement updateEvent(Evenement event);
    void deleteEvent(long id);
}
