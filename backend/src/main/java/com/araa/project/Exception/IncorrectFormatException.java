package com.araa.project.Exception;

public class IncorrectFormatException extends RuntimeException{
    public IncorrectFormatException(String message) {
        super(message);
    }

    public IncorrectFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
