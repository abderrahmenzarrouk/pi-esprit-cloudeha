package com.example.pi_projet.controllers;

import com.example.pi_projet.service.IRendezVousService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.example.pi_projet.entities.RendezVous;


import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("RendezVous")
@CrossOrigin
public class RendezVousController {
    IRendezVousService rendezVousService;

    @GetMapping("/getAllRdv")
    public List<RendezVous> getListRdv(){
        List<RendezVous>   listRdv = (List<RendezVous>) rendezVousService.recupererAllRdv();
        return listRdv;
    }

    @GetMapping("/getRdv/{idRdv}")
    public RendezVous getRdv(@PathVariable Long idRdv){
        RendezVous  Rdv = rendezVousService.recupererRdv(idRdv);
        return Rdv;
    }

    @PostMapping("/addRdv/{idGroupe}")
    public RendezVous addRdv(@RequestBody RendezVous rendezVous, @PathVariable Long idGroupe){
        return rendezVousService.addRDV(rendezVous,idGroupe);
    }

    @PostMapping("/AssignRdvToGroupe/{idRdv}/{idGroupe}")

    public RendezVous AssignRdvToGroupe(@PathVariable Long idRdv , @PathVariable Long idGroupe){
        return  rendezVousService.AssignRdvToGroupe(idRdv,idGroupe);
    }
    @PostMapping("addPoints/{idRdv}/{points}")
    public RendezVous assignPoints (@PathVariable Long idRdv, @PathVariable Float points){
        return rendezVousService.update_rdv_points(idRdv,points);
    }

    @PostMapping( "/accepterRdv/{idRdv}")
    public void accepterrdv (@PathVariable Long idRdv){
        rendezVousService.accepterPresence(idRdv);
    }

    @PostMapping("/refuserRdv/{idRdv}")
    public void refuserrdv (@PathVariable Long idRdv){
        rendezVousService.refuserPresence(idRdv);
    }

    @PutMapping("/updateRdv/{idGroupe}")
    public RendezVous upddateRdv(@RequestBody RendezVous rendezVous,@PathVariable Long idGroupe){
        return rendezVousService.updateRDV(rendezVous,idGroupe);
    }

    @PutMapping("/AssignPointsToRendezVoous/{idRdv}/{points}")
    public RendezVous AssignPointsToRendezVoous(@PathVariable Long idRdv, @PathVariable float points){
        return rendezVousService.AssignPointsToRendezVoous(idRdv, points);
    }


    @DeleteMapping("/deleteRdv/{idRdv}")
    public void deleteRdv (@PathVariable Long idRdv){
        rendezVousService.deleteRDV(idRdv);
    }


    @GetMapping("/ListeRdvByDate/{date}")
    public List<RendezVous> listrdv(@PathVariable LocalDate date){
        List<RendezVous>  listrdv = (List<RendezVous>) rendezVousService.listeRdvByDate(date);
        return listrdv;
    }

    @GetMapping("/listRdvBetweenDates/{dateDeb}/{dateFin}")
    public List<RendezVous> rendezVousList(@PathVariable LocalDate dateDeb , @PathVariable LocalDate dateFin){
        List<RendezVous> RdvsList = (List<RendezVous>) rendezVousService.ListRdvBetweenDates(dateDeb,dateFin);
        return RdvsList;
    }

    @GetMapping("/listeRdvByGroupe/{idGroupe}")
    public List<RendezVous> rendezVousByGroupeList(@PathVariable Long idGroupe) {
        List<RendezVous> rdvbygrp = (List<RendezVous>) rendezVousService.LISTRdvByGroupe(idGroupe);
        return  rdvbygrp;
    }

    @GetMapping("/heuresDisponibles/{date}/{groupId}")
    public List<String> heuresDisponibles(@PathVariable String date, @PathVariable  Long groupId) {
        LocalDate parsedDate = LocalDate.parse(date);
        return rendezVousService.heuresDisponibles(parsedDate,groupId);
    }


}
