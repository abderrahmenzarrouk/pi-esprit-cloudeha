package com.example.pi_projet.controllers;

import com.example.pi_projet.dto.EligibiliteDto;
import com.example.pi_projet.entities.Eligibilite;
import com.example.pi_projet.service.IEligibiliteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("eligibilite")
public class EligibiliteController {
    private IEligibiliteService iEligibiliteService;
    @PostMapping("/")
    public ResponseEntity<EligibiliteDto> ajouterEligibilite(@RequestBody EligibiliteDto eligibilite){
         if(iEligibiliteService.addEligibilitie(eligibilite)!=null)
             return new ResponseEntity<>(eligibilite,HttpStatus.CREATED);
              return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("/{id}")
    public  ResponseEntity<EligibiliteDto> getEligById(@PathVariable("id") int id){
        EligibiliteDto eligibilite=iEligibiliteService.getEligibilite(id);
        if(eligibilite!=null)
            return new ResponseEntity<>(eligibilite,HttpStatus.OK);
        return new ResponseEntity<>(eligibilite,HttpStatus.NOT_FOUND);
    }
    @GetMapping("/")
    public ResponseEntity<List<EligibiliteDto>> getEligibilities(){
        List<EligibiliteDto> eligibiliteDtos= iEligibiliteService.getEligibilities();
        if(!eligibiliteDtos.isEmpty())
            return new ResponseEntity<>(eligibiliteDtos,HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PutMapping("/")
    public ResponseEntity<EligibiliteDto> updateElig(@RequestBody Eligibilite eligibilite){
        EligibiliteDto eligibiliteDto=iEligibiliteService.updateEligibilite(eligibilite);
        if(eligibiliteDto!=null)
            return new ResponseEntity<>(eligibiliteDto,HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/")
    public ResponseEntity<String> removeElig(@PathVariable("id") int id){
        iEligibiliteService.deleteEligibilite(id);
        return new ResponseEntity<>("deleted ",HttpStatus.OK);
    }
}
