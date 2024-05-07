package com.example.pi_projet.repositories;


import com.example.pi_projet.entities.Classe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClasseRepository extends JpaRepository<Classe,Long> {



    @Query("SELECT DISTINCT c FROM Classe c left JOIN FETCH c.ressources")
    List<Classe> findAllWithRessources();


}
