package com.example.pi_projet.service;

import org.springframework.web.multipart.MultipartFile;
import com.example.pi_projet.entities.Attachement;

public interface IAttachementService {
    Attachement saveAttachment(MultipartFile file) throws Exception;

    Attachement getAttachment(Long fileId) throws Exception;
}
