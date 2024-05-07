package com.example.pi_projet.service;


import com.example.pi_projet.entities.Classe;
import com.example.pi_projet.repositories.ClasseRepository;
import com.example.pi_projet.repositories.RessourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.example.pi_projet.entities.ressources;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
public class RessourceServiceImp implements IRessourceService {
    @Autowired
    RessourceRepository ressourceRepository;
    @Autowired

    ClasseRepository classeRepository;
    @Override
    public ressources retrieveRessource(Long idRessource) {
        return ressourceRepository.findById(idRessource).orElse(null);
    }

    @Override
    public List<ressources> retrieveAllRessources() {
        return ressourceRepository.findAll();
    }

    @Override
    public ressources addressource(ressources ressources, MultipartFile image) {
        try {
            ressources.setImage(Base64.getEncoder().encodeToString(image.getBytes()));


        } catch (IOException e) {
            e.printStackTrace();
        }
        return ressourceRepository.save(ressources);
    }
    @Override

   public List<ressources> findAllByClassesIs(long c ){
 Classe classe= classeRepository.findById(c).get();
return ressourceRepository.findAllByClassesIs(classe);
    }



}
