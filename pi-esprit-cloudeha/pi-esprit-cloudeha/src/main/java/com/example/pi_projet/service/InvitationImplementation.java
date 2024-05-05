package com.example.pi_projet.service;

import com.example.pi_projet.entities.Groupe;
import com.example.pi_projet.entities.Invitation;
import com.example.pi_projet.entities.User;
import com.example.pi_projet.repositories.GroupeRepository;
import com.example.pi_projet.repositories.InvitationRepository;

import com.example.pi_projet.repositories.userRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class InvitationImplementation implements IInvitationService{
    userRepository userRepository;
    GroupeRepository groupeRepository;
    InvitationRepository invitationRepository;
    @Override
    public Invitation addInvitation(Long idUser, Long idGroupe) {
        Invitation invitation = new Invitation();
        User user = userRepository.findById(idUser).get();
        Groupe groupe = groupeRepository.findGroupesByIdGroupe(idGroupe);
        List<Invitation> invitationList = invitationRepository.findAll();
        invitation.setUserInvitation(user);
        invitation.setGroupeInvitation(groupe);
        invitation.setNom(user.getNom());
        invitation.setPrenom(user.getPrenom());
        long ii = groupe.getNom_Tuteur();
        invitation.setUserID( ii);
        if (!(invitationList.contains(invitation)))
            invitationRepository.save(invitation);
        return invitation;

    }

    @Override
    public void deleteInvitation(Long idInvitation) {
        invitationRepository.deleteById(idInvitation);
    }



    @Override
    public List<Invitation> retrieveInvitations(Long idGroupe) {
        List<Invitation> invitations = invitationRepository.findInvitationsByGroupeInvitation_IdGroupe(idGroupe);

        // Parcourir les invitations et charger les informations de l'utilisateur
        for (Invitation invitation : invitations) {
            Long idUtilisateur = invitation.getUserInvitation().getId();
            invitation.setUserID(idUtilisateur); // Ajouter l'ID de l'utilisateur dans l'objet Invitation
        }

        return invitations;
    }

    @Override
    public List<Invitation> retrieveInvitationByUserId(Long UserId) {
        return invitationRepository.search(UserId);
    }


}