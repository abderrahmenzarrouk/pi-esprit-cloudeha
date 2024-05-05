package com.example.pi_projet.service;

import com.example.pi_projet.entities.Groupe;
import com.example.pi_projet.entities.Post;
import com.example.pi_projet.entities.User;
import com.example.pi_projet.repositories.GroupeRepository;
import com.example.pi_projet.repositories.PostRepository;

import com.example.pi_projet.repositories.userRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PostImplementation implements IPostService{
    GroupeRepository groupeRepository;
    userRepository userRepository;
    PostRepository postRepository;
    @Override
    public Post addPost(String contenu, Long idGroupe, Long idUser) {
        Groupe groupe = groupeRepository.findGroupesByIdGroupe(idGroupe);
        User  user = userRepository.findById(idUser).get();
        Post post= new Post();
        post.setGroupePosts(groupe);
        post.setUser_post(user);
        post.setNom(user.getNom());
        post.setPrenom(user.getPrenom());
        post.setContenu(contenu);
        return postRepository.save(post);
    }

    @Override
    public Post updatePost(Post post) {
        Long id = post.getIdPost();
        Post post1 = postRepository.findPostByIdPost(id);
        String nom = post1.getNom();
        String prenom = post1.getPrenom();
        Groupe groupe = post1.getGroupePosts();
        User user = post1.getUser_post();
        post.setNom(nom);
        post.setPrenom(prenom);
        post.setGroupePosts(groupe);
        post.setUser_post(user);
        return postRepository.save(post);
    }

    @Override
    public void deletePost(Long idPost) {
        Post post = postRepository.findPostByIdPost(idPost);
        postRepository.delete(post);
    }

    @Override
    public Post retrieveSpecPost(Long idPost) {
        return postRepository.findById(idPost).get();
    }

    @Override
    public List<Post> retrievePosts(Long idGroupe) {
        List<Post> postList;
        return postList = postRepository.findPostsByGroupePosts_IdGroupe(idGroupe);
    }
}
