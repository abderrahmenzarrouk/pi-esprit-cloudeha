package tn.esprit.piproject.controllers;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.piproject.entities.Commentaire;
import tn.esprit.piproject.services.ICommentaireService;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/commentaires")
public class CommentaireController {

    private final ICommentaireService commentaireService;


    @PostMapping("/create")
    public ResponseEntity<?> createComment(@RequestBody Map<String, String> requestBody) {
        try {
            long publication_Id = Long.parseLong(requestBody.get("publication_Id"));
            String postedBy = requestBody.get("postedBy");
            String contenu = requestBody.get("contenu");
            return ResponseEntity.ok(commentaireService.createComment(publication_Id, postedBy, contenu));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }


    @GetMapping("/")
    public ResponseEntity<List<Commentaire>> findAllCommentaires() {
        List<Commentaire> commentaires = commentaireService.getAllCommentaires();
        if (!commentaires.isEmpty()) {
            return new ResponseEntity<>(commentaires, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Commentaire> findCommentaire(@PathVariable("id") long id) {
        Commentaire commentaire = commentaireService.retrieveCommentaire(id);
        if (commentaire != null) {
            return new ResponseEntity<>(commentaire, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/")
    public ResponseEntity<Commentaire> saveCommentaire(@RequestBody Commentaire commentaire) {
        Commentaire savedCommentaire = commentaireService.addCommentaire(commentaire);
        if (savedCommentaire != null) {
            return new ResponseEntity<>(savedCommentaire, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/")
    public ResponseEntity<Commentaire> modifyCommentaire(@RequestBody Commentaire commentaire) {
        Commentaire updatedCommentaire = commentaireService.updateCommentaire(commentaire);
        if (updatedCommentaire != null) {
            return new ResponseEntity<>(updatedCommentaire, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeCommentaire(@PathVariable("id") long id) {
        boolean deleted = commentaireService.deleteCommentaire(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @GetMapping("comments/{postId}")
    public ResponseEntity<?> getCommentsByPostID(@PathVariable long postId){
        try {
            return ResponseEntity.ok(commentaireService.getCommentByPostId(postId));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("something went wrong");
        }
    }

}
