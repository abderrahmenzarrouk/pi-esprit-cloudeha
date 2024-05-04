package com.example.pi_projet.service;

import com.example.pi_projet.entities.Groupe;
import com.example.pi_projet.entities.Invitation;
import com.example.pi_projet.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;


public interface IInvitationService {
    Invitation addInvitation(Long idUser, Long idGroupe);

    void deleteInvitation(Long idInvitation);

    List<Invitation> retrieveInvitations (Long idGroupe);

    List<Invitation> retrieveInvitationByUserId(Long UserId);
}