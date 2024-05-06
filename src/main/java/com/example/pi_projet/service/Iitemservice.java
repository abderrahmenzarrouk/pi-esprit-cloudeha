package com.example.pi_projet.service;

import com.example.pi_projet.entities.TypeItem;
import com.example.pi_projet.entities.items;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface Iitemservice {
    //    List<Items> getAllItems();
    items getItemById(Long id);
    items saveItem(String nom, String description, int nombreDePoints, MultipartFile imageBytes, String typeItem, String mode, MultipartFile documentationFile);
    void deleteItem(Long id);
    items updateItem(Long id, items newItem);
    List<items> searchItems(String nom, Integer nombreDePoints, TypeItem typeItem);
    items  updateItemNote (long id,float note);




}
