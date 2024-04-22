package com.example.pi_projet.repositories;


import com.example.pi_projet.entities.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Repository
@Transactional( readOnly = true)
public interface userRepository  extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);


    @Override
    List<User> findAll();


    List<User> findByUserRoleId(int id);

    @Transactional
    @Modifying
    @Query("UPDATE User a " +
            "SET a.enabled = TRUE WHERE a.email = ?1")
    int enableUser(String email);

    User findById(long id);
}
