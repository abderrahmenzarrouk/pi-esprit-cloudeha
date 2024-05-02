package com.example.shop.controllers;

import com.example.shop.models.Commentaire;
import com.example.shop.models.Items;
import com.example.shop.models.TypeItem;
import com.example.shop.repository.ItemRepository;
import com.example.shop.services.ItemServiceImp;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@CrossOrigin("*")

@RequestMapping("/items")
public class ItemContoller {
    @Autowired
    private ItemServiceImp itemService;
    @Autowired
    private ItemRepository itemRepository;
    @GetMapping("/retrive-all")
    public List<Items> getAllItems() {
//        return itemService.getAllItems();
        return itemRepository.findAll();
    }

    @GetMapping("/{id}")
    public Items getItemById(@PathVariable Long id) {
        return itemService.getItemById(id);
    }
    @PostMapping("/add-item")
    public ResponseEntity<?> addItem(
            @RequestParam("nom") String nom,
            @RequestParam("description") String description,
            @RequestParam("nombreDePoints") int nombreDePoints,
            @RequestParam("image") MultipartFile image,
            @RequestParam("typeItem") String typeItem,
            @RequestParam(value = "mode", required = false) String mode) {
        try {
            byte[] imageBytes = image.getBytes();

            // Vérifiez si le typeItem est FORMATION et déterminez le mode en conséquence
            if ("FORMATION".equals(typeItem)) {
                mode = determineMode(mode);
            } else {
                mode = null; // Mode null pour le typeItem différent de "FORMATION"
            }

            // Appelez la méthode saveItemWithMode avec le mode déterminé
           itemService.saveItem(nom, description, nombreDePoints, image, typeItem, mode);

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
    public Items updateItem(@PathVariable Long id, @RequestBody Items newItem) {
        return itemService.updateItem(id, newItem);
    }

    @DeleteMapping("/delete-item/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
        String message = "Le skieur avec l'ID " + id + " a été supprimé.";
        return ResponseEntity.ok(message);
    }
    @PostMapping("/{itemId}/commentaires")
    public ResponseEntity<String> commenterItem(@PathVariable Long itemId, @RequestBody String contenuCommentaire) {
        itemService.ajouterCommentaire(itemId, contenuCommentaire);
        return ResponseEntity.ok("Commentaire ajouté avec succès.");
    }
    /**@GetMapping("/{itemId}/getcommentaires")
    public ResponseEntity<List<Commentaire>> getAllCommentairesByItemId(@PathVariable Long itemId) {
        List<Commentaire> commentaires = itemService.getAllCommentairesByItemId(itemId);
        return ResponseEntity.ok(commentaires);
    }*/
    @GetMapping("/{itemId}/getcommentaires")
    public ResponseEntity<Map<String, Object>> getAllCommentairesByItemId(@PathVariable Long itemId) {
        Map<String, Object> response = itemService.getAllCommentairesByItemId(itemId);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/search")
    public List<Items> searchItems(
            @RequestParam(required = false) String nom,
            @RequestParam(required = false) Integer nombreDePoints,
            @RequestParam(required = false) TypeItem typeItem
    ) {
        return itemService.searchItems(nom, nombreDePoints, typeItem);
    }



}
