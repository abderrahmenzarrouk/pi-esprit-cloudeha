package com.example.pi_projet.service;

import com.example.pi_projet.repositories.IAttachementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import com.example.pi_projet.entities.Attachement;


@Service

public class AttachementService implements  IAttachementService{
    @Autowired
    private IAttachementRepository iAttachementRepository;
    @Override
    public Attachement saveAttachment(MultipartFile file) throws Exception {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if(fileName.contains("..")) {
                throw  new Exception("Filename contains invalid path sequence "
                        + fileName);
            }

            Attachement attachment
                    = new Attachement(fileName,
                    file.getContentType(),
                    file.getBytes());
            return iAttachementRepository.save(attachment);

        } catch (Exception e) {
            throw new Exception("Could not save File: " + fileName);
        }
    }

    @Override
    public Attachement getAttachment(Long fileId) throws Exception {
        return iAttachementRepository
                .findById(fileId)
                .orElseThrow(
                        () -> new Exception("File not found with Id: " + fileId));
    }
}
