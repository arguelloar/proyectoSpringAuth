package com.araa.project.Exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.ZonedDateTime;

@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ApiException{

    private final String message;
    private final HttpStatus httpStatus;
    private final ZonedDateTime timestamp;
    private final String path;

    public ApiException(String message, HttpStatus httpStatus, ZonedDateTime timestamp, String path) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.timestamp = timestamp;
        this.path = path;
    }
}