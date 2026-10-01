package org.todo.exception;

public class InvalidFileTypeException extends RuntimeException{
    public InvalidFileTypeException(String fileType){
        super("File content type "+ fileType + "is not supported");
    }
}
