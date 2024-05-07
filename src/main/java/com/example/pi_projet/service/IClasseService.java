package com.example.pi_projet.service;
import com.example.pi_projet.entities.Classe;
import org.springframework.web.multipart.MultipartFile;
import com.example.pi_projet.entities.ressources;

import java.util.List;

public interface IClasseService {
    Classe addClasse(Classe classe, MultipartFile image);
    Classe updateClasse(Classe classe);
    void removeClasse(Long idClasse);

    Classe retrieveClasse(Long idClasse);

    List<Classe> retrieveAllClasses();

    List<Classe> addAllClasses(List<Classe> classes);
    void affecterRessourceAClasse(long idClasse, List<Long> ressources);
    List<ressources> retrieveClasseRessources(long idClasse);



}
