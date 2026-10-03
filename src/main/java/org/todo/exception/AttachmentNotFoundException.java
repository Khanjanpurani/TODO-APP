package org.todo.exception;

public class AttachmentNotFoundException extends RuntimeException{
    public AttachmentNotFoundException(){
        super("No such attachment found");
    }
}
