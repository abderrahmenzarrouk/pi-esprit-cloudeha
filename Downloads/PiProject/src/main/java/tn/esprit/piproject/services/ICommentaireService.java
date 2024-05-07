package tn.esprit.piproject.services;

import tn.esprit.piproject.entities.Commentaire;

import java.util.List;

public interface ICommentaireService {
    List<Commentaire> getAllCommentaires();
    Commentaire retrieveCommentaire(long id);
    Commentaire addCommentaire(Commentaire commentaire);
    Commentaire updateCommentaire(Commentaire commentaire);
    boolean deleteCommentaire(long id);

    Commentaire createComment(Long publication_id,String postedBy , String content);
    List<Commentaire> getCommentByPostId(long postId);


}
