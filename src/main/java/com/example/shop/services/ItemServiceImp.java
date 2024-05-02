package com.example.shop.services;

import com.example.shop.models.Commentaire;
import com.example.shop.models.Items;
import com.example.shop.models.Mode;
import com.example.shop.models.TypeItem;
import com.example.shop.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

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
    public Items getItemById(Long id) {
        Optional<Items> itemOptional = itemRepository.findById(id);
        return itemOptional.orElse(null);
    }

    @Override
    public Items saveItem(String nom, String description, int nombreDePoints, MultipartFile image, String typeItem, String mode)  {
        // Créer une nouvelle instance d'Item en utilisant les paramètres fournis
        Items item = new Items();
        item.setNom(nom);
        item.setDescription(description);
        item.setNombreDePoints(nombreDePoints);
        String filename= StringUtils.cleanPath(image.getOriginalFilename());
        try {
            item.setImage(Base64.getEncoder().encodeToString(image.getBytes()));
        }catch (IOException e){
            e.printStackTrace();
        }

        item.setTypeItem(TypeItem.valueOf(typeItem));
        item.setMode(mode != null ? Mode.valueOf(mode) : null);

        // Enregistrer l'item dans la base de données
        itemRepository.save(item);
        return item;
    }
    @Override
    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }

    @Override
    public Items updateItem(Long id, Items newItem) {
        Optional<Items> existingItemOptional = itemRepository.findById(id);
        if (existingItemOptional.isPresent()) {
            Items existingItem = existingItemOptional.get();

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

    public void ajouterCommentaire(Long itemId, String contenuCommentaire) {
        // Récupérer l'item sur lequel commenter
        Items item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NoSuchElementException("Item non trouvé avec l'ID : " + itemId));

        // Créer un nouveau commentaire
        Commentaire commentaire = new Commentaire();
        commentaire.setContenu(contenuCommentaire);
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
        Items item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NoSuchElementException("Item non trouvé avec l'ID : " + itemId));

        Map<String, Object> response = new HashMap<>();
        response.put("itemId", itemId);
        response.put("commentaires", item.getCommentaires());

        return response;
    }
    @Override
    public List<Items> searchItems(String nom, Integer nombreDePoints, TypeItem typeItem) {
        return itemRepository.searchItems(nom, nombreDePoints, typeItem);
    }
}

