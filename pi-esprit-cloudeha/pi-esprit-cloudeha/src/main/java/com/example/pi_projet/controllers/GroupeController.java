package com.example.pi_projet.controllers;

import com.example.pi_projet.entities.User;
import com.example.pi_projet.service.IGroupeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import com.example.pi_projet.entities.Groupe;
import com.example.pi_projet.entities.Option;


import java.time.Year;
import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/Groupe")
@CrossOrigin("*")
public class GroupeController {
    IGroupeService groupeService;


    @GetMapping("/getAllGroupes")
    public List<Groupe> getAllGroupes(){
        List<Groupe> listGroupes = (List<Groupe>) groupeService.recupererAllGroupes();
        return listGroupes;
    }

    @GetMapping("/getGroupe/{idGroupe}")
    public Groupe getGroupe(@PathVariable Long idGroupe){
        Groupe grp = groupeService.recupererGroupe(idGroupe);
        return  grp;
    }

    @GetMapping("/getrole/{iduser}")
    public String recuperRole(@PathVariable Long iduser){
        return  groupeService.recperRoleUSer(iduser);
    }

    @PostMapping("/addGroupe/{idUser}")
    public Groupe addGroupe(@RequestBody Groupe groupe, @PathVariable Long idUser) {
        return groupeService.addGroupe(groupe,idUser);
    }

    @PutMapping("/updateGroupe")
    public  Groupe updateGroupe(@RequestBody Groupe groupe){
        return groupeService.updateGroupe(groupe);
    }

    @DeleteMapping("/deleteGroupe/{idGroupe}")
    public void deleteGroupe(@PathVariable Long idGroupe) {
        groupeService.deleteGroupe(idGroupe);
    }

    @GetMapping("/listGroupeByOptions/{option}")
    public List<Groupe> listGroupeOption (@PathVariable Option option){
        return groupeService.listeGroupesByOptions(option);
    }

    @GetMapping("/listGroupeByAnnee/{annee_scolaire}")
    public List<Groupe> listeGroupeAnne (@PathVariable Year annee_scolaire){
        return groupeService.ListGroupeByAnneScolaireI(annee_scolaire);
    }

    @GetMapping("/ListGroupePerUser/{idUser}")
    public Set<Groupe> listeGroupe (@PathVariable Long idUser)
    {
        return groupeService.retrieveGroupeToUser(idUser);
    }

    @GetMapping("/nomAdmin/{idGroupe}")
    public Long nomadmin (@PathVariable Long idGroupe)
    {
        return groupeService.NomAdmin(idGroupe);
    }

    @GetMapping("/listTuteursPerGroupe/{idGroupe}")
    public List<Long> listeTuteurs (@PathVariable Long idGroupe) {
        List<Long> listeGroupe = (List<Long>) groupeService.getTuteursIdsByGroupId(idGroupe);
        return listeGroupe;
    }

    @PostMapping("/AssignUserTOGroupe/{idinvi}")
    public Groupe AssignUserTOGroupe( @PathVariable Long idinvi){
        return groupeService.AssignUserTOGroupe(idinvi);
    }

    @GetMapping("/getotherMembers/{idUser}")
    public List<User> listeMembers (@PathVariable Long idUser) {
        List<User> listeGroupe = (List<User>) groupeService.getOtherGroupMembers(idUser);
        return listeGroupe;
    }

}
