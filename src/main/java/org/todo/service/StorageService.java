package org.todo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.todo.exception.FileEmptyException;
import org.todo.exception.FileStorageException;
import org.todo.pojos.Attachment;
import org.todo.pojos.Note;
import org.todo.repository.AttachmentRepository;

import java.io.IOException;

@Service
public class StorageService {


    AttachmentRepository attachmentRepository;
    FileValidator fileValidator;


    public StorageService(AttachmentRepository attachmentRepository,
                          FileValidator fileValidator) {
        this.attachmentRepository = attachmentRepository;
        this.fileValidator = fileValidator;
    }

    public void store(MultipartFile file, Note note) {
        fileValidator.validate(file);
        Attachment attachment = new Attachment();
        attachment.setNote(note);
        try {
            attachment.setData(file.getBytes());
        } catch (Exception e) {
            throw new FileStorageException( "Failed to store file", e);
        }
        attachment.setOriginalFileName(file.getOriginalFilename());
        attachment.setContentType(file.getContentType());
        attachment.setFileSize(file.getSize());
        attachmentRepository.save(attachment);
    }
}
