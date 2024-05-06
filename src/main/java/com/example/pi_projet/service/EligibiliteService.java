package com.example.pi_projet.service;

import com.example.pi_projet.entities.Eligibilite;
import com.example.pi_projet.repositories.IEligibiliteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EligibiliteService implements IEligibiliteService {
    private IEligibiliteRepository iEligibiliteRepository;

    @Override
    public List<Eligibilite> getEligibilities() {
        return iEligibiliteRepository.findAll();
    }

    @Override
    public Eligibilite getEligibilite(int id) {
        return iEligibiliteRepository.findById(id).get();
    }

    @Override
    public Eligibilite addEligibilitie(Eligibilite eligibilite) {
        return iEligibiliteRepository.save(eligibilite);
    }

    @Override
    public Eligibilite updateEligibilite(Eligibilite eligibilite) {
        return iEligibiliteRepository.save(eligibilite);
    }

    @Override
    public void deleteEligibilite(int id) {
      iEligibiliteRepository.deleteById(id);
    }
}
