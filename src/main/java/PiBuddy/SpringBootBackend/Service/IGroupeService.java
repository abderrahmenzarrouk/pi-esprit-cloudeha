package PiBuddy.SpringBootBackend.Service;

import PiBuddy.SpringBootBackend.Entity.Classe;
import PiBuddy.SpringBootBackend.Entity.Groupe;

import java.util.List;

public interface IGroupeService {
    Groupe addGroupe(Groupe groupe);

    Groupe retrieveGroupe(Long idGroupe);

    List<Groupe> retrieveAllGroupes();
}
