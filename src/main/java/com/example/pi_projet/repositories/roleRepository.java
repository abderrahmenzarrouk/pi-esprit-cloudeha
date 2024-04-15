package com.example.pi_projet.repositories;

import com.example.pi_projet.entities.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface roleRepository extends JpaRepository<Roles, Integer> {
}
