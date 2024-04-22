package com.example.pi_projet.controllers;

import com.example.pi_projet.entities.Reclamation;
import com.example.pi_projet.repositories.reclamationRepository;
import com.example.pi_projet.service.reclamationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/reclamations")
public class reclamationController {
    @Autowired
    reclamationService reclamationService;
    @Autowired
    private reclamationRepository reclamationReposiory;
    @PostMapping("/add-reclamation")
    public Reclamation addReclamation(@RequestBody Reclamation reclamation){ return reclamationService.addReclamation(reclamation);}
    @GetMapping("/all")
    public List<Reclamation> getReclamation(){
        return reclamationReposiory.findAll();
    }
    @PostMapping("/get-by-user")
    public List<Reclamation> getreclamationbyuser(@RequestBody Map<String, Integer> requestBody){
        int userId = requestBody.get("userid");
        return reclamationReposiory.findByUser_Id(userId);}
    @PostMapping("/reclamation-traitee")
    public void enablereclamation(@RequestBody Map<String, Integer> requestBody) {
        long reclamationId = requestBody.get("id");
        Optional<Reclamation> r = reclamationReposiory.findById(reclamationId);
        r.get().setEtatreclamation(1);
        reclamationReposiory.save(r.get());
    }


}
