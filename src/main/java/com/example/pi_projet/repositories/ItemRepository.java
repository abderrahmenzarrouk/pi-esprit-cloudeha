package com.example.pi_projet.repositories;


import com.example.pi_projet.entities.TypeItem;
import com.example.pi_projet.entities.items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<items,Long> {
    Optional<items> findById(Long itemId);
    List<items> findAll();
    @Query("SELECT i FROM items i WHERE " +
            "(:nom IS NULL OR i.nom LIKE %:nom%) AND " +
            "(:nombreDePoints IS NULL OR i.nombreDePoints = :nombreDePoints) AND " +
            "(:typeItem IS NULL OR i.typeItem = :typeItem)")
    List<items> searchItems(
            @Param("nom") String nom,
            @Param("nombreDePoints") Integer nombreDePoints,
            @Param("typeItem") TypeItem typeItem
    );


}
