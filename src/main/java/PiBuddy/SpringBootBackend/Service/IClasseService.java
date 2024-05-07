package PiBuddy.SpringBootBackend.Service;

import PiBuddy.SpringBootBackend.Entity.Classe;
import PiBuddy.SpringBootBackend.Entity.ressources;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IClasseService {
    Classe addClasse(Classe classe, MultipartFile image);
    Classe updateClasse(Classe classe);
    void removeClasse(Long idClasse);

    Classe retrieveClasse(Long idClasse);

    List<Classe> retrieveAllClasses();

    List<Classe> addAllClasses(List<Classe> classes);
    void affecterRessourceAClasse(long idClasse, List<Long> ressources);
    List<ressources> retrieveClasseRessources(long idClasse);



}
