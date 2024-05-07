package PiBuddy.SpringBootBackend.Controller;

import PiBuddy.SpringBootBackend.Entity.Classe;
import PiBuddy.SpringBootBackend.Entity.Groupe;
import PiBuddy.SpringBootBackend.Service.IGroupeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/groupes")
@CrossOrigin("*")

public class GroupeController {
    @Autowired

    private IGroupeService groupeService;

    @GetMapping("/getAllGroupes")
    public List<Groupe> getAllGroupes(){
        return groupeService.retrieveAllGroupes();
    }

    @PostMapping("/add-groupe")
    public Groupe addGroupe(@RequestBody Groupe groupe){
        return groupeService.addGroupe(groupe);
    }
}
