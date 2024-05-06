package com.example.pi_projet.service;

import com.example.pi_projet.dto.EligibiliteDto;
import com.example.pi_projet.entities.Eligibilite;
import com.example.pi_projet.repositories.IEligibiliteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EligibiliteService implements IEligibiliteService {
    private IEligibiliteRepository iEligibiliteRepository;
  public EligibiliteDto  convertToDto(Eligibilite eligibilite){
      EligibiliteDto eligibiliteDto = new EligibiliteDto();
      eligibiliteDto.setGroupe(eligibilite.getGroupe());
      eligibiliteDto.setLien_Youtube(eligibilite.getLien_Youtube());
      eligibiliteDto.setScore_vote(eligibilite.getScore_vote());
      eligibiliteDto.setNote_collectif_moyenne(eligibilite.getNote_collectif_moyenne());
      return eligibiliteDto;
    }
    @Override
    public List<EligibiliteDto> getEligibilities() {
        List<Eligibilite> eligibilites =iEligibiliteRepository.findAll();
        return  eligibilites.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public EligibiliteDto getEligibilite(int id) {
         Eligibilite eligibilite=iEligibiliteRepository.findById(id).get();
         return convertToDto(eligibilite);
    }

    @Override
    public EligibiliteDto addEligibilitie(EligibiliteDto eligibiliteDto) {
             Eligibilite eligibilite=new Eligibilite();
             eligibilite.toEntity(eligibiliteDto);
         iEligibiliteRepository.save(eligibilite);
         return eligibiliteDto;
    }

    @Override
    public EligibiliteDto updateEligibilite(Eligibilite eligibilite) {
        return convertToDto(iEligibiliteRepository.save(eligibilite));
    }

    @Override
    public void deleteEligibilite(int id) {
      iEligibiliteRepository.deleteById(id);
    }
}
