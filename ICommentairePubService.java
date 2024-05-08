package com.example.pi_projet.services;

import com.example.pi_projet.entities.CommentairePub;

import java.util.List;

public interface ICommentairePubService {
    List<CommentairePub> getAllCommentaires();
    CommentairePub retrieveCommentaire(long id);
    CommentairePub addCommentaire(CommentairePub commentairePub);
    CommentairePub updateCommentaire(CommentairePub commentairePub);
    boolean deleteCommentaire(long id);

    CommentairePub createComment(Long publication_id, String postedBy , String content);
    List<CommentairePub> getCommentByPostId(long postId);


}
