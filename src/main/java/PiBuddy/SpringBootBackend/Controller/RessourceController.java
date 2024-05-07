package PiBuddy.SpringBootBackend.Controller;

import PiBuddy.SpringBootBackend.Entity.Classe;
import PiBuddy.SpringBootBackend.Entity.Typeressource;
import PiBuddy.SpringBootBackend.Entity.ressources;
import PiBuddy.SpringBootBackend.Service.IClasseService;
import PiBuddy.SpringBootBackend.Service.IRessourceService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@AllArgsConstructor
@RestController
@RequestMapping("/ressources")
@CrossOrigin("*")
public class RessourceController {
    @Autowired

    private IRessourceService ressourceService;

    @GetMapping("/getAllRessources")
    public List<ressources> getAllRessources() {
        return ressourceService.retrieveAllRessources();
    }
    @PostMapping("/add-ressource")
    public ressources addRessource(@RequestParam("Typeressource") Typeressource Typeressource,

                            @RequestParam("image") MultipartFile image) {
        ressources r = new ressources();
        r.setTyperessource(Typeressource);

        return ressourceService.addressource(r,image);
    }
@GetMapping("/getClasseRessources/{id}")
public List<ressources> retrieveClasseRessources(@PathVariable("id") Long idClasse) {

    return  ressourceService.findAllByClassesIs(idClasse);
}
}
