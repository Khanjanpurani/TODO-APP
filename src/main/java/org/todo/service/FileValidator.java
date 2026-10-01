package org.todo.service;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.todo.exception.FileEmptyException;
import org.todo.exception.InvalidFileTypeException;

import java.util.Set;

@Service
public class FileValidator {

    private static final Set<String> ALLOWED_TYPES = Set.of(
            "image/jpeg",
            "image/png",
            "application/pdf",
            "text/plain"
    );


    public void isFileEmpty(MultipartFile multipartFile){
        if(multipartFile.isEmpty()){
            throw  new FileEmptyException(multipartFile.getOriginalFilename());
        }
    }

    public void isFileTypeValid(MultipartFile multipartFile){
        if(!ALLOWED_TYPES.contains(multipartFile.getContentType())){
            throw new InvalidFileTypeException(multipartFile.getContentType());
        }
    }

    public void validate(MultipartFile file) {
        isFileEmpty(file);
        isFileTypeValid(file);
    }
}
