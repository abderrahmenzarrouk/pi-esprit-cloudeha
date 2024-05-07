package com.esprit.piproject.services;

import com.esprit.piproject.entities.Evenement;
import com.esprit.piproject.repositories.IEvenementRepository;
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
