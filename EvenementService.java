package com.example.pi_projet.services;

import com.example.pi_projet.entities.Evenement;
import com.example.pi_projet.repositories.IEvenementRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EvenementService implements IEvenementSerivce {
    private IEvenementRepository iEvenementRepository;

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
        iEvenementRepository.deleteById(id);
    }

}
