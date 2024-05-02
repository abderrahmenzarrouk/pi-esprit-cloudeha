package com.example.shop.repository;

import com.example.shop.models.Items;
import com.example.shop.models.TypeItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Items,Long> {
    Optional<Items> findById(Long itemId);
    List<Items> findAll();
    @Query("SELECT i FROM Items i WHERE " +
            "(:nom IS NULL OR i.nom LIKE %:nom%) AND " +
            "(:nombreDePoints IS NULL OR i.nombreDePoints = :nombreDePoints) AND " +
            "(:typeItem IS NULL OR i.typeItem = :typeItem)")
    List<Items> searchItems(
            @Param("nom") String nom,
            @Param("nombreDePoints") Integer nombreDePoints,
            @Param("typeItem") TypeItem typeItem
    );


}
