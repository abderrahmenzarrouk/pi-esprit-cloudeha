package com.example.pi_projet.service;

import com.example.pi_projet.entities.Commentaire;
import com.example.pi_projet.entities.Mode;
import com.example.pi_projet.entities.TypeItem;
import com.example.pi_projet.entities.items;
import com.example.pi_projet.repositories.ItemRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ItemServiceImp implements Iitemservice{
    @Autowired
    private ItemRepository itemRepository;

/*    @Override
    public List<Items> getAllItems() {
        List<Items> items = itemRepository.findAll();
        for (Items item : items) {
            if (item.getImage() != null) {
                // Encodage de l'image en base64
                String base64EncodedImage = Base64.getEncoder().encodeToString(item.getImage());
                // Remplacement de l'image par sa version encodée en base64
                item.setImage(base64EncodedImage.getBytes());
            }
        }
        return items;
    }*/

    @Override
    public items getItemById(Long id) {
        Optional<items> itemOptional = itemRepository.findById(id);
        return itemOptional.orElse(null);
    }

    @Override
    public items saveItem(String nom, String description, int nombreDePoints, MultipartFile image, String typeItem, String mode, MultipartFile documentationFile) {
        // Créer une nouvelle instance d'Item en utilisant les paramètres fournis
        items item = new items();
        item.setNom(nom);
        item.setDescription(description);
        item.setNombreDePoints(nombreDePoints);

        try {
            // Vérifier si l'image est fournie
            if (image != null && !image.isEmpty()) {
                // Convertir l'image en tableau de bytes et l'enregistrer dans l'entité
                item.setImage(Base64.getEncoder().encodeToString(image.getBytes()));
            }

            item.setTypeItem(TypeItem.valueOf(typeItem));
            item.setMode(mode != null ? Mode.valueOf(mode) : null);

            // Vérifier si le type d'élément est "DOCUMENTATION" et si le fichier de documentation est fourni
            if (TypeItem.DOCUMENTATION.name().equals(typeItem) && documentationFile != null && !documentationFile.isEmpty()) {
                // Lire le contenu du fichier de documentation en tant que tableau de bytes
                byte[] docBytes = documentationFile.getBytes();

                // Convertir les données du fichier en Base64 et les enregistrer dans l'entité
                String base64Doc = Base64.getEncoder().encodeToString(docBytes);
                item.setDocumentation(base64Doc.getBytes());
            }

            // Enregistrer l'item dans la base de données
            itemRepository.save(item);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return item;
    }
    @Override
    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }

    @Override
    public items updateItem(Long id, items newItem) {
        Optional<items> existingItemOptional = itemRepository.findById(id);
        if (existingItemOptional.isPresent()) {
            items existingItem = existingItemOptional.get();

            // Vérifiez les champs de newItem s'ils ne sont pas null, puis mettez à jour seulement ces champs
            if (newItem.getNom() != null) {
                existingItem.setNom(newItem.getNom());
            }
            if (newItem.getDescription() != null) {
                existingItem.setDescription(newItem.getDescription());
            }
            if (newItem.getNombreDePoints() != 0) {
                existingItem.setNombreDePoints(newItem.getNombreDePoints());
            }
            if (newItem.getImage() != null) {
                existingItem.setImage(newItem.getImage());
            }
            if (newItem.getTypeItem() != null) {
                existingItem.setTypeItem(newItem.getTypeItem());
            }
            if (newItem.getMode() != null) {
                existingItem.setMode(newItem.getMode());
            }

            return itemRepository.save(existingItem);
        } else {
            // Ou lancez une exception pour indiquer que l'élément n'existe pas
            return null;
        }
    }

    public void ajouterCommentaire(Long itemId, String contenuCommentaire, String nom) {
        // Récupérer l'item sur lequel commenter
        items item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NoSuchElementException("Item non trouvé avec l'ID : " + itemId));

        // Créer un nouveau commentaire
        Commentaire commentaire = new Commentaire();
        commentaire.setContenu(contenuCommentaire);
        commentaire.setNom(nom);
        commentaire.setItem(item); // Associer le commentaire à l'item

        // Ajouter le commentaire à la liste des commentaires de l'item
        item.getCommentaires().add(commentaire);

        // Enregistrer les modifications dans la base de données
        itemRepository.save(item);
    }
    /** public List<Commentaire> getAllCommentairesByItemId(Long itemId) {
     // Récupérer l'item par son ID
     Items item = itemRepository.findById(itemId)
     .orElseThrow(() -> new NoSuchElementException("Item non trouvé avec l'ID : " + itemId));

     // Récupérer tous les commentaires associés à cet item
     return item.getCommentaires();
     }*/
    public Map<String, Object> getAllCommentairesByItemId(Long itemId) {
        items item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NoSuchElementException("Item non trouvé avec l'ID : " + itemId));

        Map<String, Object> response = new HashMap<>();
        response.put("itemId", itemId);
        response.put("commentaires", item.getCommentaires());

        return response;
    }
    @Override
    public List<items> searchItems(String nom, Integer nombreDePoints, TypeItem typeItem) {
        return itemRepository.searchItems(nom, nombreDePoints, typeItem);
    }
    @Override
    public items updateItemNote(long id, float note) {
        items items = itemRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Plat not found with id: " + id));
        float i = items.getNote();
        System.out.println("note pres"+i);
        float notefinal = i+note;
        System.out.println("notefinal"+notefinal);
        System.out.println(notefinal / 2f);
        items.setNote( notefinal / 2f);
        return itemRepository.save(items);
    }
}