package com.example.shop.services;

import com.example.shop.models.Items;
import com.example.shop.models.TypeItem;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface Iitemservice {
//    List<Items> getAllItems();
    Items getItemById(Long id);
   Items saveItem(String nom, String description, int nombreDePoints, MultipartFile imageBytes, String typeItem, String mode);
    void deleteItem(Long id);
    Items updateItem(Long id, Items newItem);
    List<Items> searchItems(String nom, Integer nombreDePoints, TypeItem typeItem);



}
