package com.example.pi_projet.service;

import com.example.pi_projet.entities.Post;

import java.util.List;

public interface IPostService {

    Post addPost(String contenu , Long idGrooupe, Long idUser);

    Post updatePost(Post post);

    void deletePost(Long idPost);

    Post retrieveSpecPost(Long idPost);
    List<Post> retrievePosts (Long idGroupe);

}
