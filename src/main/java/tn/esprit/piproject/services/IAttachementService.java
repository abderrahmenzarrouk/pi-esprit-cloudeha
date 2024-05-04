package tn.esprit.piproject.services;

import org.springframework.web.multipart.MultipartFile;
import tn.esprit.piproject.entities.Attachement;

public interface IAttachementService {
    Attachement saveAttachment(MultipartFile file) throws Exception;

    Attachement getAttachment(Long fileId) throws Exception;
}
