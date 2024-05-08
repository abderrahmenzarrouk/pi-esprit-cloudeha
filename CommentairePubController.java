package com.example.pi_projet.controllers;

import com.example.pi_projet.entities.CommentairePub;
import com.example.pi_projet.services.ICommentairePubService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/commentaires")
public class CommentairePubController {

    private final ICommentairePubService commentaireService;


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
    public ResponseEntity<List<CommentairePub>> findAllCommentaires() {
        List<CommentairePub> commentairePubs = commentaireService.getAllCommentaires();
        if (!commentairePubs.isEmpty()) {
            return new ResponseEntity<>(commentairePubs, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentairePub> findCommentaire(@PathVariable("id") long id) {
        CommentairePub commentairePub = commentaireService.retrieveCommentaire(id);
        if (commentairePub != null) {
            return new ResponseEntity<>(commentairePub, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/")
    public ResponseEntity<CommentairePub> saveCommentaire(@RequestBody CommentairePub commentairePub) {
        CommentairePub savedCommentairePub = commentaireService.addCommentaire(commentairePub);
        if (savedCommentairePub != null) {
            return new ResponseEntity<>(savedCommentairePub, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/")
    public ResponseEntity<CommentairePub> modifyCommentaire(@RequestBody CommentairePub commentairePub) {
        CommentairePub updatedCommentairePub = commentaireService.updateCommentaire(commentairePub);
        if (updatedCommentairePub != null) {
            return new ResponseEntity<>(updatedCommentairePub, HttpStatus.OK);
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
