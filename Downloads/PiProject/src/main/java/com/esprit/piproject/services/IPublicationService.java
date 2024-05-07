package com.esprit.piproject.services;

import com.esprit.piproject.entities.Publication;

import java.util.List;

public interface IPublicationService {
    List<Publication> getAllPublications();
    Publication retrievePublication(long id);
    Publication addPublication(Publication publication);
    Publication updatePublication(Publication publication);
    boolean deletePublication(long id);
    void likePost(Long pubId);
    List<Publication>searchByName(String name);

}
