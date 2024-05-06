package com.example.pi_projet.controllers;

import com.example.pi_projet.entities.Eligibilite;
import com.example.pi_projet.service.IEligibiliteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("eligibilite")
public class EligibiliteController {
    private IEligibiliteService iEligibiliteService;
    @PostMapping("/")
    public ResponseEntity<Eligibilite> ajouterEligibilite(@RequestBody Eligibilite eligibilite){
         if(iEligibiliteService.addEligibilitie(eligibilite)!=null)
             return new ResponseEntity<>(eligibilite,HttpStatus.CREATED);
              return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
