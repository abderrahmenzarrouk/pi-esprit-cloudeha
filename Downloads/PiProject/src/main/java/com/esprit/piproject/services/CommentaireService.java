package com.esprit.piproject.services;

import com.esprit.piproject.entities.Publication;
import com.esprit.piproject.repositories.ICommentaireRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.esprit.piproject.entities.Commentaire;
import com.esprit.piproject.repositories.IPublicationRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentaireService implements ICommentaireService {

    private final ICommentaireRepository commentaireRepository;

    @Autowired
    private IPublicationRepository publicationRepository;

    @Override
    public List<Commentaire> getAllCommentaires() {
        return commentaireRepository.findAll();
    }

    @Override
    public Commentaire retrieveCommentaire(long id) {
        return commentaireRepository.findById(id).orElse(null);
    }

    @Override
    public Commentaire addCommentaire(Commentaire commentaire) {
        return commentaireRepository.save(commentaire);
    }

    @Override
    public Commentaire updateCommentaire(Commentaire commentaire) {
        return commentaireRepository.save(commentaire);
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
    public Commentaire createComment(Long publication_id,String postedBy , String content){

      Optional<Publication> optionalPublication=publicationRepository.findById(publication_id);
      if (optionalPublication.isPresent()){
          Commentaire comment = new Commentaire();
          comment.setPublication(optionalPublication.get());
          comment.setContenu(content);
          comment.setPostedBy(postedBy);
          comment.setDateCommentaire(new Date());

        return commentaireRepository.save(comment);
      }
      throw new EntityNotFoundException("post not found");
    }

    @Override
    public List<Commentaire> getCommentByPostId(long postId){
        return commentaireRepository.findByPublication_Id(postId);
    }


}
