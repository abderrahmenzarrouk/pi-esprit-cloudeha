package com.example.pi_projet.service;


import com.example.pi_projet.entities.Classe;
import com.example.pi_projet.entities.ressources;
import com.example.pi_projet.repositories.ClasseRepository;
import com.example.pi_projet.repositories.RessourceRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class ClasseServiceImp implements IClasseService {
    private final ClasseRepository classeRepository;
    @Autowired
    RessourceRepository ressourceRepository;

    public ClasseServiceImp(ClasseRepository classeRepository) {
        this.classeRepository = classeRepository;
    }

    @Override
    public Classe addClasse(Classe classe, MultipartFile image) {
        try {
            classe.setImage(Base64.getEncoder().encodeToString(image.getBytes()));


        } catch (IOException e) {
            e.printStackTrace();
        }
        return classeRepository.save(classe);
    }

    @Override
    public Classe updateClasse(Classe classe) {
        return classeRepository.save(classe);
    }

    @Override
    public void removeClasse(Long idClasse) {
        classeRepository.deleteById(idClasse);
    }

    @Override
    public Classe retrieveClasse(Long idClasse) {
        return classeRepository.findById(idClasse).orElse(null);
    }

    @Override
    public List<Classe> retrieveAllClasses() {
        return classeRepository.findAll();
    }

    @Override
    public List<Classe> addAllClasses(List<Classe> classes) {
        return classeRepository.saveAll(classes);
    }

    @Override
    public void affecterRessourceAClasse(long idClasse, List<Long> ressources) {
        Classe classe = classeRepository.findById(idClasse).get();


        List<ressources> ressourcess = ressourceRepository.findAllById(ressources);

        classe.setRessources(ressourcess);
        classeRepository.save(classe);

    }

    @Override
    public List<ressources> retrieveClasseRessources(long idClasse) {

        Optional<Classe> classeOptional = classeRepository.findById(idClasse);
        if (classeOptional.isPresent()) {
            Classe classe = classeOptional.get();
            return classe.getRessources();
        } else {
            throw new RuntimeException("Classe non trouvée avec l'ID: " + idClasse);
        }
    }
    }




