package com.example.pi_projet.controllers;

import com.example.pi_projet.entities.Post;

import com.example.pi_projet.service.IPostService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/Post")
@CrossOrigin("*")
public class PostController {
    IPostService postService;
    @PostMapping("/addPost/{contenu}/{idGroupe}/{idUser}")
    public Post addPost (@PathVariable String contenu, @PathVariable Long idGroupe , @PathVariable Long idUser)
    {
        return postService.addPost(contenu,idGroupe,idUser);
    }


    @PutMapping("/updatePost")
    public Post updatePost (@RequestBody Post post){
        return postService.updatePost(post);

    }


    @DeleteMapping("/deletePost/{idPost}")
    public void deletePost (@PathVariable Long idPost) {
        postService.deletePost(idPost);
    }

    @GetMapping("/retrieveAllPosts/{idGroupe}")
    public List<Post> retrieveAll(@PathVariable Long idGroupe){
        return postService.retrievePosts(idGroupe);
    }

}