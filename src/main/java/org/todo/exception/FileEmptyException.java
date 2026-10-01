package org.todo.exception;

import java.io.File;

public class FileEmptyException extends RuntimeException{

    public FileEmptyException(String fileName){
        super("File " + fileName + "is empty");
    }
}
