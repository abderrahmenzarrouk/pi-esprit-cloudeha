package com.example.pi_projet.service;

import com.example.pi_projet.entities.Groupe;
import com.example.pi_projet.entities.Option;
import com.example.pi_projet.entities.RendezVous;
import com.example.pi_projet.entities.User;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.Year;
import java.util.List;
import java.util.Set;

public interface IGroupeService {
    Groupe addGroupe (Groupe groupe, Long idUser);
    Groupe updateGroupe (Groupe groupe);
    void deleteGroupe (Long idGroupe);

    Groupe recupererGroupe (Long idGroupe);

    List<Groupe> recupererAllGroupes ();

    List<Groupe> listeGroupesByOptions (Option option);

    List<Groupe> ListGroupeByAnneScolaireI (Year Annee_Scolaire);

    // Groupe AssignUserToGroupeByInvitation(Long idInvitation);

    Groupe AssignUserTOGroupe(Long idinvi);

    Set<Groupe> retrieveGroupeToUser(Long idUser);

    Long NomAdmin(Long idGroupe);

    @Scheduled(cron = "0 * * * * * ")
    void AssignTuteurToGroupe();

    List<Long> getTuteursIdsByGroupId(Long groupId);

    @Scheduled(cron = "0 * * * * * ")
    void calculateTotalScore();

    List<User> getOtherGroupMembers(long userId);
}
