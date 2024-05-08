package com.example.pi_projet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.pi_projet.entities.CommentairePub;

import java.util.List;

public interface ICommentairePubRepository extends JpaRepository<CommentairePub, Long> {


    List<CommentairePub> findByPublication_Id(long publication_id);

}
