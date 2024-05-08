package com.example.pi_projet.controllers;

import com.example.pi_projet.entities.Publication;
import com.example.pi_projet.services.IPublicationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/publications")
@CrossOrigin("*")
public class PublicationController {

    private final IPublicationService publicationService;

    @GetMapping("/")
    public ResponseEntity<List<Publication>> findAllPublications() {
        List<Publication> publications = publicationService.getAllPublications();
        if (!publications.isEmpty()) {
            return new ResponseEntity<>(publications, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Publication> findPublication(@PathVariable("id") long id) {
        Publication publication = publicationService.retrievePublication(id);
        if (publication != null) {
            return new ResponseEntity<>(publication, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/")
    public ResponseEntity<Publication> savePublication(@RequestBody Publication publication) {
        Publication savedPublication = publicationService.addPublication(publication);
        if (savedPublication != null) {
            return new ResponseEntity<>(savedPublication, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/")
    public ResponseEntity<Publication> modifyPublication(@RequestBody Publication publication) {
        Publication updatedPublication = publicationService.updatePublication(publication);
        if (updatedPublication != null) {
            return new ResponseEntity<>(updatedPublication, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removePublication(@PathVariable("id") long id) {
        boolean deleted = publicationService.deletePublication(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{postId}/like")
    public ResponseEntity<?> likePost(@PathVariable Long postId) {
        try {
            publicationService.likePost(postId);
            return ResponseEntity.ok(new String[]{"Post liked successfully."});
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<?> searchByName(@PathVariable String name){
        try {
            return  ResponseEntity.status(HttpStatus.OK).body(publicationService.searchByName(name));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



}
