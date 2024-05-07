package com.example.pi_projet.service;

import com.example.pi_projet.entities.Groupe;
import com.example.pi_projet.repositories.GroupeRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import com.example.pi_projet.entities.RendezVous;
import com.example.pi_projet.repositories.RendezVousRepository;

@Service
@AllArgsConstructor
public class RendezVousImplementation implements IRendezVousService {
    RendezVousRepository rendezVousRepository;
    GroupeRepository groupeRepository;
    @Override
    public RendezVous addRDV(RendezVous rendezVous ,Long idGroupe) {
        Groupe groupe = groupeRepository.findGroupesByIdGroupe(idGroupe);
        rendezVous.setGroupe(groupe);
        rendezVous.setEtat("Confirmer");
        rendezVous.setNombre(rendezVous.getNombre() + 1);
        this.mettreAJourEtatRendezVousExpiré();
        return rendezVousRepository.save(rendezVous);
    }

    @Override
    public RendezVous updateRDV(RendezVous rendezVous, Long idGroupe) {
        Groupe groupe = groupeRepository.findGroupesByIdGroupe(idGroupe);
        rendezVous.setGroupe(groupe);
        rendezVous.setEtat("Confirmer");
        return rendezVousRepository.save(rendezVous);
    }

    @Override
    public void deleteRDV(Long idRdv) {
        rendezVousRepository.deleteById(idRdv);
    }

    @Override
    public RendezVous recupererRdv(Long idRdv) {
        return rendezVousRepository.findById(idRdv).get();
    }

    @Override
    public List<RendezVous> recupererAllRdv() {
        return rendezVousRepository.findAll();
    }

    @Override
    public List<RendezVous> listeRdvByDate(LocalDate date) {
        return rendezVousRepository.findRendezVousByDate(date);
    }

    @Override
    public List<RendezVous> ListRdvBetweenDates(LocalDate dateDeb, LocalDate dateFin) {
        return rendezVousRepository.findRendezVousByDateBetween(dateDeb,dateFin);
    }

    @Override
    public List<RendezVous> LISTRdvByGroupe(Long idGroupe) {
        return rendezVousRepository.findRendezVousByGroupe_IdGroupe(idGroupe);
    }

    @Override
    public List<String> heuresDisponibles(LocalDate date, Long groupId) {
        List<String> heuresOccupées = rendezVousRepository.findHeureByDateAndGroupeIdGroupe(date,groupId);
        List<String> heuresDisponibles = new ArrayList<>();


        LocalTime heureDebut = LocalTime.of(8, 0);
        LocalTime heureFin = LocalTime.of(17, 0);
        while (heureDebut.isBefore(heureFin)) {
            String heure = heureDebut.toString();
            if (!heuresOccupées.contains(heure)) {
                heuresDisponibles.add(heure);
            }
            heureDebut = heureDebut.plusHours(1);
        }

        return heuresDisponibles;
    }

    @Override
    public RendezVous AssignRdvToGroupe(Long idRdv, Long idGroupe) {
        RendezVous rdv = rendezVousRepository.findByIdRdv(idRdv);
        Groupe groupe = groupeRepository.findGroupesByIdGroupe(idGroupe);

        if (rdv != null) {
            rdv.setGroupe(groupe);
            rendezVousRepository.save(rdv);

        }
        return rdv;
    }

    @Override
    public RendezVous AssignPointsToRendezVoous(Long idRdv, float points) {
        RendezVous rendezVous = rendezVousRepository.findByIdRdv(idRdv);
        if (rendezVous != null)
            rendezVous.setPoints(points);

        return rendezVousRepository.save(rendezVous);
    }

    @Override
    public void accepterPresence(Long idRdv) {
        RendezVous rendezVous = rendezVousRepository.findByIdRdv(idRdv);
        if (rendezVous != null) {
            // Incrémentez l'attribut nombre pour l'acceptation
            rendezVous.setNombre(rendezVous.getNombre() + 1);
            // Mettez à jour l'état si le nombre atteint ou dépasse 10
            if (rendezVous.getNombre() >= 30) {
                rendezVous.setEtat("Annuler");
            }

            rendezVousRepository.save(rendezVous);
        }
    }

    @Override
    public void refuserPresence(Long idRdv) {
        RendezVous rendezVous = rendezVousRepository.findByIdRdv(idRdv);
        if (rendezVous != null) {
            // Mettez l'attribut nombre à 0 pour le refus
            rendezVous.setNombre(rendezVous.getNombre()+30);
            if (rendezVous.getNombre() >= 30) {
                rendezVous.setEtat("Annuler");
            }
            rendezVousRepository.save(rendezVous);
        }
    }

    //  @Scheduled(cron = "0 * * * * * ")
    @Override
    public void mettreAJourEtatRendezVousExpiré() {
        List<RendezVous> rendezVousList = rendezVousRepository.findAll();
        LocalDate today = LocalDate.now();

        for (RendezVous rendezVous : rendezVousList) {
            if (rendezVous.getDate().isBefore(today)) {
                rendezVousRepository.delete(rendezVous);
            }
        }
    }


}
