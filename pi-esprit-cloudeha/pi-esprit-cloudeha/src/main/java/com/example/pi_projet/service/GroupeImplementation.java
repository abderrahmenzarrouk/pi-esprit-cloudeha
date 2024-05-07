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
        Long idGroupe = groupe.getIdGroupe();
        Groupe groupe1 = groupeRepository.findGroupesByIdGroupe(idGroupe);
        int idCreateur = groupe1.getNom_Tuteur();
        String statut = groupe1.getStatut();
        Float total = groupe1.getTotal_Score();

        groupe.setNom_Tuteur(idCreateur);
        groupe.setStatut(statut);
        groupe.setTotal_Score(total);
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
    public Groupe AssignUserTOGroupe(Long idInvi) {
        // User user = userRepository.findUserById(idUser);
        Invitation invitation = invitationRepository.findInvitationByIdInvitation(idInvi);
        Long idGroupe = invitation.getGroupeInvitation().getIdGroupe();
        Long idUtilisateur = invitation.getUserInvitation().getId();
        Groupe groupe = groupeRepository.findGroupesByIdGroupe(idGroupe);
        User user = userRepository.findById(idUtilisateur).get();
        System.out.println("uuuuuuuuuuuuuuuuuuusssssssssssssssseeeeeeeeeeeeeeerrrrrrrrrrr"+user);
        System.out.println("invitationnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn"+invitation);
        System.out.println("Groupeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee"+groupe);
        if (user != null && invitation != null) {


            System.out.println(groupe);
            if (groupe != null && !groupe.getUserSet().contains(user) && groupe.getEtat() < 7) {
                // Ajouter l'utilisateur au groupe
                user.getGroupeSet().add(groupe);
                groupe.getUserSet().add(user);
                groupe.setEtat(groupe.getEtat() + 1);

                // Enregistrer les modifications
                userRepository.save(user);
                groupeRepository.save(groupe);
                invitationRepository.delete(invitation);
                this.AssignTuteurToGroupe();
                return groupe;

            } else {
                throw new RuntimeException("Khnissi try again mfs");
            }
        } else {
            throw new RuntimeException("User or invitation not found");
        }

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
    public Groupe AssignUTG(Long idUser, Long idGroupe) {
        User user = userRepository.findById(idUser).get();
        Groupe groupe = groupeRepository.findGroupesByIdGroupe(idGroupe);

        if (user != null && groupe != null) {
            user.getGroupeSet().add(groupe);
            groupe.getUserSet().add(user);

            userRepository.save(user);
            groupeRepository.save(groupe);
            return groupe;
        }
        else
            throw new RuntimeException("Khnissi try again mfs");
    }
   // @Transactional
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

                if (countEtudiants == 2) {
                    log.info("ahawaaaaaaaaaaaaaaaaaaaaaaaaaaa " ,countEtudiants);
                    List<User> tuteurs = new ArrayList<>();
                    for (User user : userList) {
                        if (user.getUserRole().getRole().name().equals("Tuteur")  &&
                                (//user.getTypespecialite() == Specialite.Arctic ||
                                         user.getTypespecialite() == Specialite.DS
                                        //|| user.getTypespecialite() == Specialite.BI
                                )) {
                            tuteurs.add(user);
                        }
                    }
                    if (tuteurs.size() >= 1) {
                        User tuteur = tuteurs.get(0);
                        this.AssignUTG(tuteur.getId(), groupe.getIdGroupe());
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

    @Override
    public List<User> getOtherGroupMembers(long userId) {
        User user = userRepository.findById(userId);
        if (user == null) {
            // Gérer le cas où l'utilisateur n'existe pas
            return null;
        }

        // Récupérer le groupe de l'utilisateur
        Groupe userGroup = user.getGroupeSet().stream().findFirst().orElse(null);
        if (userGroup == null) {
            // Gérer le cas où l'utilisateur n'est pas membre d'un groupe
            return null;
        }

        // Récupérer tous les membres du groupe
        List<User> groupMembers = userGroup.getUserSet().stream().toList();

        // Filtrer les membres pour exclure l'utilisateur actuel
        return groupMembers.stream()
                .filter(member -> member.getId() != userId)
                .collect(Collectors.toList());
    }


}