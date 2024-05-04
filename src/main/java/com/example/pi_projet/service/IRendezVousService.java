package com.example.pi_projet.service;

import com.example.pi_projet.entities.RendezVous;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

public interface IRendezVousService {
    RendezVous addRDV(RendezVous rendezVous,Long idGroupe);
    RendezVous updateRDV(RendezVous rendezVous, Long idGroupe);
    void deleteRDV(Long idRdv);

    RendezVous recupererRdv(Long idRdv);

    List<RendezVous> recupererAllRdv ();

    List<RendezVous> listeRdvByDate (LocalDate date);

    List<RendezVous> ListRdvBetweenDates (LocalDate dateDeb , LocalDate dateFin);

    List<RendezVous> LISTRdvByGroupe (Long idGroupe);

    List<String> heuresDisponibles(LocalDate date, Long groupId);

    RendezVous AssignRdvToGroupe(Long idRdv , Long idGroupe);

    RendezVous AssignPointsToRendezVoous(Long idRdv, float points);

    void accepterPresence(Long idRdv);
    void refuserPresence(Long idRdv);

    void mettreAJourEtatRendezVousExpiré();
}