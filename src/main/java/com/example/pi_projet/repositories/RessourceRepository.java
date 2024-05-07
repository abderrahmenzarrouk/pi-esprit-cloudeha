package com.example.pi_projet.repositories;


import com.example.pi_projet.entities.Classe;
import com.example.pi_projet.entities.ressources;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface RessourceRepository extends JpaRepository<ressources,Long> {
    List<ressources> findAllByClassesIs(Classe c );

}
