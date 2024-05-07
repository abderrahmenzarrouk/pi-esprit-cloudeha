package tn.esprit.piproject.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.piproject.entities.Publication;
import tn.esprit.piproject.repositories.IPublicationRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class PublicationService implements IPublicationService {

    private final IPublicationRepository publicationRepository;

    @Override
    public List<Publication> getAllPublications() {
        return publicationRepository.findAll();
    }

    @Override
    public Publication retrievePublication(long id) {
        if (publicationRepository.findById(id).isPresent())
        {
            Publication p= publicationRepository.findById(id).orElse(null);
            assert p != null;
            p.setViewCount(p.getViewCount() + 1);
            updatePublication(p);
        }
        return publicationRepository.findById(id).orElse(null);
    }

    @Override
    public Publication addPublication(Publication publication) {
        publication.setLikes(0);
        publication.setViewCount(0);
        publication.setDatePublication(new Date());
        return publicationRepository.save(publication);
    }

    @Override
    public Publication updatePublication(Publication publication) {
        return publicationRepository.save(publication);
    }
    @Override
    public boolean deletePublication(long id) {
        if (publicationRepository.existsById(id)) {
            publicationRepository.deleteById(id);
            return true;
        }
        return false;
    }

@Override
    public void likePost(Long pubId) {
        Optional<Publication> optionalPost = publicationRepository.findById(pubId);
        if (optionalPost.isPresent()) {
            Publication post = optionalPost.get();
            post.setLikes(post.getLikes() + 1);
            publicationRepository.save(post);

        }else {
            throw new EntityNotFoundException("Post not found with id: " + pubId);
        }
    }

    @Override
    public List<Publication>searchByName(String name){
        return publicationRepository.findAllByNomContaining(name);
    }
}
