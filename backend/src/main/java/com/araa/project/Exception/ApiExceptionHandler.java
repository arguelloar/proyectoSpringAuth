package com.araa.project.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.ZoneId;
import java.time.ZonedDateTime;


@ControllerAdvice
public class ApiExceptionHandler {

    private static final String TIME_ZONE = "America/Los_Angeles";


    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Object> handleAuthRequest(HttpServletRequest request,AuthenticationException e){
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        ApiException apiException = new ApiException(
                e.getMessage(),
                httpStatus,
                ZonedDateTime.now(ZoneId.of(TIME_ZONE)),
                request.getRequestURI()
        );
        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleError(HttpServletRequest request, BadCredentialsException e) {
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
    public ResponseEntity<Object> handleAccessDenied(HttpServletRequest request, AccessDeniedException e) {
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        ApiException apiException = new ApiException(
                e.getMessage(),
                httpStatus,
                ZonedDateTime.now(ZoneId.of(TIME_ZONE)),
                request.getRequestURI()
        );
        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<Object> emailRegistered(HttpServletRequest request, SQLIntegrityConstraintViolationException e) {
        HttpStatus httpStatus = HttpStatus.FORBIDDEN;
        ApiException apiException = new ApiException(
                "Email already registered",
                httpStatus,
                ZonedDateTime.now(ZoneId.of(TIME_ZONE)),
                request.getRequestURI()
        );
        return new ResponseEntity<>(apiException, httpStatus);
    }

}