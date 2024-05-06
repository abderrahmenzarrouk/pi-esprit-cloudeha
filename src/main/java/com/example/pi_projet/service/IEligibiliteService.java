package com.example.pi_projet.service;

import com.example.pi_projet.dto.EligibiliteDto;
import com.example.pi_projet.entities.Eligibilite;

import java.util.List;

public interface IEligibiliteService {
    List<EligibiliteDto> getEligibilities();
    EligibiliteDto getEligibilite(int id);
    EligibiliteDto addEligibilitie(EligibiliteDto eligibiliteDto);
    EligibiliteDto updateEligibilite(Eligibilite eligibilite);
    void deleteEligibilite(int id);
}
