package com.example.pi_projet.service;

import org.springframework.web.multipart.MultipartFile;
import com.example.pi_projet.entities.ressources;

import java.util.List;

public interface IRessourceService {
    ressources retrieveRessource(Long idRessouce);

    List<ressources> retrieveAllRessources();
    ressources addressource(ressources ressources, MultipartFile image);
    List<ressources> findAllByClassesIs(long c );


}
