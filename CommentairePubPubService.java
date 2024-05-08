package com.example.pi_projet.services;

import com.example.pi_projet.entities.Publication;
import com.example.pi_projet.repositories.ICommentairePubRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.pi_projet.entities.CommentairePub;
import com.example.pi_projet.repositories.IPublicationRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentairePubPubService implements ICommentairePubService {

    private final ICommentairePubRepository commentaireRepository;

    @Autowired
    private IPublicationRepository publicationRepository;

    @Override
    public List<CommentairePub> getAllCommentaires() {
        return commentaireRepository.findAll();
    }

    @Override
    public CommentairePub retrieveCommentaire(long id) {
        return commentaireRepository.findById(id).orElse(null);
    }

    @Override
    public CommentairePub addCommentaire(CommentairePub commentairePub) {
        return commentaireRepository.save(commentairePub);
    }

    @Override
    public CommentairePub updateCommentaire(CommentairePub commentairePub) {
        return commentaireRepository.save(commentairePub);
    }

    @Override
    public boolean deleteCommentaire(long id) {
        if (commentaireRepository.existsById(id)) {
            commentaireRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public CommentairePub createComment(Long publication_id, String postedBy , String content){

      Optional<Publication> optionalPublication=publicationRepository.findById(publication_id);
      if (optionalPublication.isPresent()){
          CommentairePub comment = new CommentairePub();
          comment.setPublication(optionalPublication.get());
          comment.setContenu(content);
          comment.setPostedBy(postedBy);
          comment.setDateCommentaire(new Date());

        return commentaireRepository.save(comment);
      }
      throw new EntityNotFoundException("post not found");
    }

    @Override
    public List<CommentairePub> getCommentByPostId(long postId){
        return commentaireRepository.findByPublication_Id(postId);
    }


}
