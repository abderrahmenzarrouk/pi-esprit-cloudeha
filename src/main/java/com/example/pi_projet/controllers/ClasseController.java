package com.example.pi_projet.controllers;


import com.example.pi_projet.entities.Classe;
import com.example.pi_projet.service.IClasseService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.pi_projet.entities.ressources;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/classes")
@CrossOrigin("*")
public class ClasseController {

    @Autowired

    private IClasseService classeService;

    @GetMapping("/getAllClasses")
    public List<Classe> getAllClasses() {
        return classeService.retrieveAllClasses();
    }


    @GetMapping("/getClassById/{idClasse}")
    public Classe getClassById(@PathVariable Long idClasse) {
        return classeService.retrieveClasse(idClasse);
    }


    @PostMapping("/add-classe")
    public Classe addClasse(@RequestParam("etage") int etage,
                            @RequestParam("num") String num,
                            @RequestParam("bloc") String bloc,
                            @RequestParam("image") MultipartFile image) {
        Classe classe = new Classe(etage, num, bloc);
        classe.setEtage(etage);
        classe.setNum(num);
        classe.setBloc(bloc);
        classe.setDispo(true);
        return classeService.addClasse(classe, image);
    }

    //    @PutMapping("/update-classe")
//    public Classe updateClasse(@RequestBody Classe classe){
//        return classeService.updateClasse(classe);
//    }
    @PutMapping("/classe/{idClasse}")
    public ResponseEntity<Classe> updateClasse(@PathVariable long idClasse, @RequestBody Classe classe) {
        Classe existingClasse = classeService.retrieveClasse(idClasse);

        if (existingClasse == null)
            return ResponseEntity.notFound().build();

        existingClasse.setEtage(classe.getEtage());
        if (classe.getNum() != null)
            existingClasse.setNum(classe.getNum());
        if (classe.getBloc() != null)
            existingClasse.setBloc(classe.getBloc());
        if (classe.getImage() != null)
            existingClasse.setImage(classe.getImage());

        Classe updatedClasse = classeService.updateClasse(existingClasse);

        return ResponseEntity.ok(updatedClasse);
    }


    @DeleteMapping("/delete-classe/{idClasse}")
    public void removeClasse(@PathVariable Long idClasse) {
        classeService.removeClasse(idClasse);
    }

    @PostMapping("/add-list-classes")
    public List<Classe> addListClasse(@RequestBody List<Classe> classes) {
        return classeService.addAllClasses(classes);
    }


    @PostMapping("/add-classe-ressources/{idClasse}")
    public void addressourcestoclasse(@PathVariable Long idClasse, @RequestBody List<Long> ressources) {
        classeService.affecterRessourceAClasse(idClasse,ressources);

    }
    @GetMapping("/{id}/ressources")
    public ResponseEntity<List<ressources>> retrieveClasseRessources(@PathVariable("id") Long idClasse) {
        List<ressources> ressources = classeService.retrieveClasseRessources(idClasse);
        return new ResponseEntity<>(ressources, HttpStatus.OK);
    }

}

