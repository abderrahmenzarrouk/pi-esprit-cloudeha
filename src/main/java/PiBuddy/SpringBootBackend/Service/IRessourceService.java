package PiBuddy.SpringBootBackend.Service;

import PiBuddy.SpringBootBackend.Entity.Classe;
import PiBuddy.SpringBootBackend.Entity.ressources;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IRessourceService {
    ressources retrieveRessource(Long idRessouce);

    List<ressources> retrieveAllRessources();
    ressources addressource(ressources ressources, MultipartFile image);
    List<ressources> findAllByClassesIs(long c );


}
