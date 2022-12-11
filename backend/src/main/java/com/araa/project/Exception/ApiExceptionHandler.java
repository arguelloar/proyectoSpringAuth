package com.araa.project.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;


@ControllerAdvice
public class ApiExceptionHandler {

    private static final String TIME_ZONE = "America/Los_Angeles";


    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Object> handleAuthRequest(HttpServletRequest request){
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        ApiException apiException = new ApiException(
                "You could not get authenticated",
                httpStatus,
                ZonedDateTime.now(ZoneId.of(TIME_ZONE)),
                request.getRequestURI()
        );
        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleError(HttpServletRequest request, BadCredentialsException e) throws IOException {
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        ApiException apiException = new ApiException(
                e.getMessage(),
                httpStatus,
                ZonedDateTime.now(ZoneId.of(TIME_ZONE)),
                request.getRequestURI()
        );
        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDenied(HttpServletRequest request) throws IOException {
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        ApiException apiException = new ApiException(
                "You have no authorization for this resource",
                httpStatus,
                ZonedDateTime.now(ZoneId.of(TIME_ZONE)),
                request.getRequestURI()
        );
        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(EmailAlreadyRegisteredException.class)
    public ResponseEntity<Object> emailRegistered(HttpServletRequest request, EmailAlreadyRegisteredException e) {
        HttpStatus httpStatus = HttpStatus.FORBIDDEN;
        ApiException apiException = new ApiException(
                e.getMessage(),
                httpStatus,
                ZonedDateTime.now(ZoneId.of(TIME_ZONE)),
                request.getRequestURI()
        );
        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(IncorrectFormatException.class)
    public ResponseEntity<Object> incorrectFormat(HttpServletRequest request, IncorrectFormatException e) {
        HttpStatus httpStatus = HttpStatus.FORBIDDEN;
        ApiException apiException = new ApiException(
                e.getMessage(),
                httpStatus,
                ZonedDateTime.now(ZoneId.of(TIME_ZONE)),
                request.getRequestURI()
        );
        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Object> incorrectProductId(HttpServletRequest request, ProductNotFoundException e) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ApiException apiException = new ApiException(
                e.getMessage(),
                httpStatus,
                ZonedDateTime.now(ZoneId.of(TIME_ZONE)),
                request.getRequestURI()
        );
        return new ResponseEntity<>(apiException, httpStatus);
    }
}