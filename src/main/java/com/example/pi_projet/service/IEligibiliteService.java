package com.example.pi_projet.service;

import com.example.pi_projet.entities.Eligibilite;

import java.util.List;

public interface IEligibiliteService {
    List<Eligibilite> getEligibilities();
    Eligibilite getEligibilite(int id);
    Eligibilite addEligibilitie(Eligibilite eligibilite);
    Eligibilite updateEligibilite(Eligibilite eligibilite);
    void deleteEligibilite(int id);
}
