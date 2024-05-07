package com.example.pi_projet.repositories;


import com.example.pi_projet.entities.ResponsePost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResponsPostRepository extends JpaRepository<ResponsePost, Long> {

    ResponsePost findByIdResponse(Long idResponse);
    List<ResponsePost> findResponsePostsByPostIdPost(Long idPost);
}
