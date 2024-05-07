package com.example.pi_projet.service;

import com.example.pi_projet.entities.Post;
import com.example.pi_projet.entities.ResponsePost;
import com.example.pi_projet.entities.User;
import com.example.pi_projet.repositories.PostRepository;
import com.example.pi_projet.repositories.ResponsPostRepository;

import com.example.pi_projet.repositories.userRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ResponsePostImplementation implements IResponsePostService{

    IPostService postService;
    PostRepository postRepository;
    userRepository userRepository;
    ResponsPostRepository responsPostRepository;
    @Override
    public ResponsePost addResponsePost(String responsePost, Long idPost, Long idUser) {
        ResponsePost responsePost1 = new ResponsePost();
        Post post =     postRepository.findPostByIdPost(idPost);
        User user = userRepository.findById(idUser).get();
        responsePost1.setPost(post);
        responsePost1.setNom(user.getNom());
        responsePost1.setPrenom(user.getPrenom());
        responsePost1.setContenu(responsePost);
        responsePost1.setUser_ResponsePost(user);
        return responsPostRepository.save(responsePost1);
    }

    @Override
    public ResponsePost updateResponsePost(ResponsePost responsePost) {
        Long idResponse = responsePost.getIdResponse();
        ResponsePost responsePost1 = responsPostRepository.findByIdResponse(idResponse);
        Post post = responsePost1.getPost();
        User user = responsePost1.getUser_ResponsePost();
        String nom = responsePost1.getNom();
        String prenom = responsePost1.getPrenom();
        responsePost.setNom(nom);
        responsePost.setUser_ResponsePost(user);
        responsePost.setPost(post);
        responsePost.setPrenom(prenom);
        return responsPostRepository.save(responsePost);
    }

    @Override
    public void deleteResponsePost(Long idResponse) {
        ResponsePost responsePost = responsPostRepository.findByIdResponse(idResponse);
responsPostRepository.delete(responsePost);
    }

    @Override
    public List<ResponsePost> retrieveAllResponsePerPost(Long idPost) {
        return responsPostRepository.findResponsePostsByPostIdPost(idPost);
    }
}
