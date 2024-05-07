package PiBuddy.SpringBootBackend.Service;

import PiBuddy.SpringBootBackend.Entity.Classe;
import PiBuddy.SpringBootBackend.Entity.Groupe;
import PiBuddy.SpringBootBackend.Repository.GroupeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupeServiceImp implements IGroupeService{
    private final GroupeRepository groupeRepository;

    public GroupeServiceImp(GroupeRepository groupeRepository) {
        this.groupeRepository = groupeRepository;
    }

    @Override
    public Groupe addGroupe(Groupe groupe) {
        return groupeRepository.save(groupe);
    }
    @Override
    public Groupe retrieveGroupe(Long idGroupe) {
        return groupeRepository.findById(idGroupe).orElse(null);
    }
    @Override
    public List<Groupe> retrieveAllGroupes() {
        return groupeRepository.findAll();
    }



}
