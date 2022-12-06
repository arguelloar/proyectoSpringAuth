package com.araa.project.Exception;

import org.springframework.dao.DataIntegrityViolationException;

public class EmailAlreadyRegisteredException extends DataIntegrityViolationException {
    public EmailAlreadyRegisteredException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public EmailAlreadyRegisteredException(String msg) {
        super(msg);
    }
}
