package org.todo.exception;

public class FileStorageException extends RuntimeException{

    public FileStorageException(String msg, Throwable e){
        super(msg + e);
    }
}
