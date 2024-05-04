package com.example.pi_projet.service;

import com.example.pi_projet.entities.*;
import com.example.pi_projet.repositories.InvitationRepository;
import com.example.pi_projet.repositories.RendezVousRepository;

import com.example.pi_projet.repositories.userRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.example.pi_projet.repositories.GroupeRepository;

import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class GroupeImplementation implements IGroupeService{
    userRepository userRepository;
    GroupeRepository groupeRepository;
    RendezVousRepository rendezVousRepository;
    InvitationRepository invitationRepository;
    @Override
    public Groupe addGroupe(Groupe groupe, Long idUser) {
        List<Groupe> groupeList = groupeRepository.findAll();
        User user =  userRepository.findById(idUser).get();
        groupe.setUserSet(Collections.singleton(user));
        user.setGroupeSet(Collections.singleton(groupe));
        groupe.setNom_Tuteur((int)user.getId());
        groupe.setStatut("ENCORE DES PLACES");
        groupe.setEtat(1);
        //groupe.setStatut((User) user.getUserRole().getRole().toString()); //bech tekhou mtaa l user li conn
        return groupeRepository.save(groupe);

    }

    @Override
    public Groupe updateGroupe(Groupe groupe) {
        return groupeRepository.save(groupe);
    }

    @Override
    public void deleteGroupe(Long idGroupe) {
        groupeRepository.deleteById(idGroupe);
    }

    @Override
    public Groupe recupererGroupe(Long idGroupe) {
        return groupeRepository.findById(idGroupe).get();
    }

    @Override
    public List<Groupe> recupererAllGroupes() {
        return groupeRepository.findAll();
    }

    @Override
    public List<Groupe> listeGroupesByOptions(Option option) {
        return groupeRepository.findGroupeByOption(option);
    }

    @Override
    public List<Groupe> ListGroupeByAnneScolaireI(Year Annee_Scolaire) {
        return groupeRepository.SelectPerYear(Annee_Scolaire);
    }

    @Override
    public Groupe AssignUserTOGroupe(Long idUser, Long idGroupe, Long idinvi) { //hne bech nzidodha ken l user admin
        User user = userRepository.findById(idUser).get();
        Groupe groupe = groupeRepository.findGroupesByIdGroupe(idGroupe);
        Invitation invitation = invitationRepository.findInvitationByIdInvitation(idinvi);
        Optional<User> userOptional = groupe.getUserSet().stream().findFirst();
        User user1 = userOptional.orElse(null);


        if (user != null && groupe != null && !(groupe.getUserSet().contains(user)) && groupe.getEtat() < 7 ) {

            user.getGroupeSet().add(groupe);
            groupe.getUserSet().add(user);
            groupe.setEtat(groupe.getEtat()+1);

            userRepository.save(user);
            groupeRepository.save(groupe);
            invitationRepository.delete(invitation);
            return groupe;
        }




        else
            throw new RuntimeException("Khnissi try again mfs");
    }

    @Override
    public Set<Groupe> retrieveGroupeToUser(Long idUser) {
        Optional<User> userOptional =  userRepository.findById(idUser);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return user.getGroupeSet();
        } else {
            return Collections.emptySet();
        }
    }

    @Override
    public Long NomAdmin(Long idGroupe) {
        Groupe groupe = groupeRepository.findGroupesByIdGroupe(idGroupe);
        return (long) groupe.getNom_Tuteur();
    }

    @Transactional
    //  @Scheduled(cron = "0 * * * * * ")
    @Override
    public void AssignTuteurToGroupe() {
        List<Groupe> groupeList = groupeRepository.findAll();
        List<User> userList = userRepository.findAll();

        for (Groupe groupe : groupeList) {

            if (groupe.getOption().name().equals(Option.ArcTIC.name())) {
                int countEtudiants = 0;
                for (User user : groupe.getUserSet()) {
                    if (user.getUserRole().getRole().name().equals("Etudiant") ) {
                        countEtudiants++;
                    }
                }

                if (countEtudiants == 1) {
                    log.info("ahawaaaaaaaaaaaaaaaaaaaaaaaaaaa " ,countEtudiants);
                    List<User> tuteurs = new ArrayList<>();
                    for (User user : userList) {
                        if (user.getUserRole().getRole().name().equals("Tuteur")  &&
                                (user.getTypespecialite() == Specialite.Arctic
                                        //    || user.getTypespecialite() == Specialite.DS ||
                                        //user.getTypespecialite() == Specialite.BI
                                )) {
                            tuteurs.add(user);
                        }
                    }
                    if (tuteurs.size() >= 1) {
                        User tuteur = tuteurs.get(0);
                        this.AssignUserTOGroupe(tuteur.getId(), groupe.getIdGroupe(),null);
                    }
                }
                else {
                    log.info("mafama haaaaaaaaaaaaaaaaaaaadddddddddddd " ,countEtudiants);
                }
            }
        }
    }


    @Override
    public List<Long> getTuteursIdsByGroupId(Long groupId) {
        List<Long> tuteursIds = new ArrayList<>();

        Groupe groupe = groupeRepository.findGroupesByIdGroupe(groupId);

        if (groupe != null) {
            Set<User> users = groupe.getUserSet();
            for (User user : users) {
                if (user.getUserRole().getRole().name().equals("Tuteur")) {
                    tuteursIds.add(user.getId());
                }
            }
        }

        return tuteursIds;
    }



    //  @Scheduled(cron = "0 * * * * * ")
    @Override
    public void calculateTotalScore() {
        List<RendezVous> rendezVousList = rendezVousRepository.findAll();
        List<Groupe> groupeList = groupeRepository.findAll();
        Float total =0.0F;
        Map<Long, Integer> totalScores = new HashMap<>(); // Map pr stocker les scores totaux per groupe ya jon

        for (RendezVous rendezVous : rendezVousList) {
            Long groupId = rendezVous.getGroupe().getIdGroupe();
            int points = (int) rendezVous.getPoints();

            totalScores.put(groupId, totalScores.getOrDefault(groupId, 0) + points);
        }

        for (Groupe groupe : groupeList) {
            Long groupId = groupe.getIdGroupe();
            int totalScore = totalScores.getOrDefault(groupId, 0);
            groupe.setTotal_Score(totalScore);
            groupeRepository.save(groupe);
            System.out.println("Score total pour le groupe " + groupId + " : " + totalScore);
        }



        log.info(String.valueOf(totalScores));
    }





}