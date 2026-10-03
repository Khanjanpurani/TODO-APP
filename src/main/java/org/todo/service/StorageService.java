package org.todo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.todo.dto.AttachmentResponse;
import org.todo.dto.NoteResponse;
import org.todo.exception.AttachmentNotFoundException;
import org.todo.exception.FileEmptyException;
import org.todo.exception.FileStorageException;
import org.todo.pojos.Attachment;
import org.todo.pojos.Note;
import org.todo.repository.AttachmentRepository;

import java.io.IOException;
import java.util.List;

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

    public Attachment getAttachmentById(int id) {
        return attachmentRepository.findById(id)
                .orElseThrow(AttachmentNotFoundException::new);
    }

    public List<Attachment> getAllAttachmentByNote(Note note){
        return attachmentRepository.findByNote(note);
    }


    private AttachmentResponse convertToResponse(Attachment attachment) {
        return new AttachmentResponse(attachment.getId(),attachment.getOriginalFileName(),attachment.getContentType(),attachment.getFileSize());
    }

    public Attachment getAttachment(Note note, int attachmentID) {
        return attachmentRepository.findByIdAndNote(attachmentID, note)
                .orElseThrow(() ->
                        new AttachmentNotFoundException());
    }

    public void deleteAttachment(Note note, int attachmentID) {

        Attachment attachment = getAttachment(note, attachmentID);

        attachmentRepository.delete(attachment);
    }
}
