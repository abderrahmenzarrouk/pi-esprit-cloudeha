package com.example.pi_projet.controllers;

import com.example.pi_projet.entities.Invitation;

import com.example.pi_projet.service.IInvitationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/Invitation")
@CrossOrigin("*")
public class InvitationController {
    IInvitationService invitationService;


    @PostMapping("/addInvitation/{idUser}/{idGroupe}")
    public Invitation addInvitation(@PathVariable Long idUser , @PathVariable Long idGroupe)
    {
        return invitationService.addInvitation(idUser,idGroupe);
    }

    @DeleteMapping("/deleteInvitation/{idInvitation}")
    public void deleteInvitation(@PathVariable Long idInvitation) {
        // Logique de suppression de l'invitation en utilisant l'ID fourni
        invitationService.deleteInvitation(idInvitation);

        // Retourner une réponse de succès

    }

    @GetMapping("/retrieveAllInvitation/{idGroupe}")
    public List<Invitation> retrieveAll (@PathVariable Long idGroupe){
        return invitationService.retrieveInvitations(idGroupe);
    }

    @GetMapping("/listInvitationByUserId/{UserId}")
    public List<Invitation> listInvi(@PathVariable Long UserId) {
        return  invitationService.retrieveInvitationByUserId(UserId);
    }

}
