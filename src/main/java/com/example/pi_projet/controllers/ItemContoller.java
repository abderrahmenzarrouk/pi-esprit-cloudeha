package com.example.pi_projet.controllers;

import com.example.pi_projet.entities.Commentaire;
import com.example.pi_projet.entities.TypeItem;
import com.example.pi_projet.entities.items;
import com.example.pi_projet.repositories.CommentaireRepository;
import com.example.pi_projet.repositories.ItemRepository;
import com.example.pi_projet.service.ItemServiceImp;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@RestController
@CrossOrigin("*")

@RequestMapping("/items")
public class ItemContoller {
    @Autowired
    private ItemServiceImp itemService;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private CommentaireRepository commentaireRepository;
    @GetMapping("/retrive-all")
    public List<items> getAllItems() {
//        return itemService.getAllItems();
        return itemRepository.findAll();
    }

    @GetMapping("/{id}")
    public items getItemById(@PathVariable Long id) {
        return itemService.getItemById(id);
    }
    @PostMapping("/add-item")
    public ResponseEntity<?> addItem(
            @RequestParam("nom") String nom,
            @RequestParam("description") String description,
            @RequestParam("nombreDePoints") int nombreDePoints,
            @RequestParam("image") MultipartFile image,
            @RequestParam("typeItem") String typeItem,
            @RequestParam(value = "mode", required = false) String mode,
            @RequestParam(value = "documentationFile", required = false) MultipartFile documentationFile) {

        try {
            byte[] imageBytes = image.getBytes();

            // Vérifiez si le typeItem est FORMATION et déterminez le mode en conséquence
            if ("FORMATION".equals(typeItem)) {
                mode = determineMode(mode);
            } else {
                mode = null; // Mode null pour le typeItem différent de "FORMATION"
            }

            // Appelez la méthode saveItem avec le mode déterminé et le fichier de documentation
            itemService.saveItem(nom, description, nombreDePoints, image, typeItem, mode, documentationFile);

            return ResponseEntity.ok("Item ajouté avec succès.");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Une erreur s'est produite lors de l'ajout de l'item.");
        }
    }


    private String determineMode(String mode) {
        // Déterminez le mode en fonction de la valeur reçue
        return "LIGNE".equals(mode) ? "LIGNE" : "PRESENT";
    }


    @PutMapping("/{id}")
    public items updateItem(@PathVariable Long id, @RequestBody items newItem) {
        return itemService.updateItem(id, newItem);
    }

    @DeleteMapping("/delete-item/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
        String message = "Le skieur avec l'ID " + id + " a été supprimé.";
        return ResponseEntity.ok(message);
    }
    @PostMapping("/{nomu}/{itemId}/commentaires")
    public ResponseEntity<String> commenterItem(@PathVariable String nomu,@PathVariable Long itemId, @RequestBody Map<String, String> contenuCommentaire) {
        itemService.ajouterCommentaire(itemId, contenuCommentaire.get("contenu"),nomu);
        return ResponseEntity.ok("Commentaire ajouté avec succès.");
    }
    /**@GetMapping("/{itemId}/getcommentaires")
    public ResponseEntity<List<Commentaire>> getAllCommentairesByItemId(@PathVariable Long itemId) {
    List<Commentaire> commentaires = itemService.getAllCommentairesByItemId(itemId);
    return ResponseEntity.ok(commentaires);
    }*/
    @GetMapping("/{itemId}/getcommentaires")
    public List<Commentaire> getAllCommentairesByItemId(@PathVariable Long itemId) {
        items items = itemRepository.findById(itemId).get();
        List<Commentaire> commentaires =  commentaireRepository.findByItem(items);
        return commentaires;




    }
    @GetMapping("/search")
    public List<items> searchItems(
            @RequestParam(required = false) String nom,
            @RequestParam(required = false) Integer nombreDePoints,
            @RequestParam(required = false) TypeItem typeItem
    ) {
        return itemService.searchItems(nom, nombreDePoints, typeItem);
    }
    @PutMapping("/{id}/note")
    public ResponseEntity<items> updateItemNote(@PathVariable("id") long id, @RequestBody float note) {
        try {
            items updatedItem = itemService.updateItemNote(id, note);
            return ResponseEntity.ok(updatedItem);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }


}