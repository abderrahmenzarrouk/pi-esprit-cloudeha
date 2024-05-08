package PiBuddy.SpringBootBackend.Service;

import PiBuddy.SpringBootBackend.Entity.Classe;
import PiBuddy.SpringBootBackend.Entity.EtatReservation;
import PiBuddy.SpringBootBackend.Entity.Groupe;
import PiBuddy.SpringBootBackend.Entity.Reservation;
import PiBuddy.SpringBootBackend.Repository.ClasseRepository;
import PiBuddy.SpringBootBackend.Repository.GroupeRepository;
import PiBuddy.SpringBootBackend.Repository.ReservationRepository;
import ch.qos.logback.classic.Logger;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class ReservationServiceImp implements IReservationService {
    private ReservationRepository reservationRepository;
    private  ClasseRepository classeRepository;
    private GroupeRepository groupeRepository;


    @Override
    public Reservation addReservation(long idClasse, long idGroupe, LocalDateTime debutReservation, LocalDateTime finReservation) {
        Optional<Classe> classeOptional = classeRepository.findById(idClasse);
        Optional<Groupe> groupeOptional = groupeRepository.findById(idGroupe);

        if (classeOptional.isPresent() && groupeOptional.isPresent() && classeOptional.get().isDispo()) {
            Classe classe = classeOptional.get();
            Groupe groupe = groupeOptional.get();
            classe.setDispo(false);
            Reservation reservation = new Reservation();
            reservation.setClasse(classe);
            reservation.setGroupe(groupe);
            reservation.setEtat(EtatReservation.EN_ATTENTE);
            reservation.setDebutReservation(debutReservation);
            reservation.setFinReservation(finReservation);

            return reservationRepository.save(reservation);
        } else {
            throw new IllegalArgumentException("Classe or groupe not found");
        }
    }

    @Override
    public Reservation getReservationById(Long idReservation) {
        Optional<Reservation> reservationOptional = reservationRepository.findById(idReservation);
        return reservationOptional.orElse(null);
    }

    @Override
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation getReservationByClasse(Long idClasse) {
        return reservationRepository.findByClasseIdClasse(idClasse).orElse(null);
    }

    @Override
    public Reservation updateReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    public void deleteReservationById(Long idReservation) {
        reservationRepository.deleteById(idReservation);
    }

//    @Override
//    public List<Reservation> getReservationsValidees() {
//        return null;
//    }

//     Méthode pour valider une réservation
    public Reservation validerReservation(Long idReservation) {
        Reservation reservation = reservationRepository.findById(idReservation)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

        // Mettre l'état de la réservation à "VALIDE"
        reservation.setEtat(EtatReservation.VALIDE);
        return reservationRepository.save(reservation);
    }

    // Méthode pour rejeter une réservation
    public Reservation rejeterReservation(Long idReservation) {
        Reservation reservation = reservationRepository.findById(idReservation)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

        // Mettre l'état de la réservation à "REJETE"
        reservation.setEtat(EtatReservation.REJETE);
        return reservationRepository.save(reservation);
    }

//    @Scheduled(fixedRate = 60000) // Vérifie les réservations expirées toutes les minutes
//    @Scheduled(cron = "0 */30 * * * *") // Exécution toutes les 30 minutes
    public void updateClassAvailability() {
        LocalDateTime now = LocalDateTime.now();
        List<Reservation> expiredReservations = reservationRepository.findByFinReservationBefore(now);
        for (Reservation reservation : expiredReservations) {
            Classe classe = reservation.getClasse();
            classe.setDispo(true); // La classe est à nouveau disponible
            classeRepository.save(classe);
        }
    }

    public Map<String, Integer> getStatsHeures() {
        // Effectuez une requête pour récupérer toutes les réservations
        List<Reservation> reservations = reservationRepository.findAll();

        // Initialisez un HashMap pour stocker les statistiques par heure
        Map<String, Integer> statsHeures = new HashMap<>();
        for (int i = 0; i < 24; i++) {
            statsHeures.put(String.valueOf(i), 0);
        }

        // Parcourez chaque réservation pour l'agrégation
        for (Reservation reservation : reservations) {
            LocalDateTime debut = reservation.getDebutReservation();
            LocalDateTime fin = reservation.getFinReservation();

            // Obtenez l'heure de début et de fin de la réservation
            int heureDebut = debut.getHour();
            int heureFin = fin.getHour();

            // Incrémentez les statistiques pour chaque heure de la réservation
            for (int i = heureDebut; i <= heureFin; i++) {
                statsHeures.put(String.valueOf(i), statsHeures.getOrDefault(String.valueOf(i), 0) + 1);
            }
        }

        return statsHeures;
    }

    public Map<DayOfWeek, Integer> getStatsJoursSemaine() {
        // Effectuez une requête pour récupérer toutes les réservations
        List<Reservation> reservations = reservationRepository.findAll();

        // Initialisez un HashMap pour stocker les statistiques par jour de la semaine
        Map<DayOfWeek, Integer> statsJoursSemaine = new HashMap<>();

        // Parcourez chaque réservation pour l'agrégation
        for (Reservation reservation : reservations) {
            LocalDate debut = reservation.getDebutReservation().toLocalDate();
            LocalDate fin = reservation.getFinReservation().toLocalDate();

            // Incrémentez les statistiques pour chaque jour de la semaine de la réservation
            for (LocalDate date = debut; !date.isAfter(fin); date = date.plusDays(1)) {
                DayOfWeek jourSemaine = date.getDayOfWeek();
                statsJoursSemaine.put(jourSemaine, statsJoursSemaine.getOrDefault(jourSemaine, 0) + 1);
            }
        }

        return statsJoursSemaine;


    }
}

