package com.example.pi_projet.repositories;

import com.example.pi_projet.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findPostsByGroupePosts_IdGroupe(Long idGroupe);

    Post findPostByIdPost(Long idPost);

}