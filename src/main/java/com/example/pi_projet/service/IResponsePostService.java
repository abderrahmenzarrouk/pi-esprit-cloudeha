package com.example.pi_projet.service;

import com.example.pi_projet.entities.ResponsePost;

import java.util.List;

public interface IResponsePostService {

    ResponsePost addResponsePost(String responsePost, Long idPost, Long idUser);

    ResponsePost updateResponsePost(ResponsePost responsePost);

    void deleteResponsePost(Long idResponse);

    List<ResponsePost> retrieveAllResponsePerPost (Long idPost);

}