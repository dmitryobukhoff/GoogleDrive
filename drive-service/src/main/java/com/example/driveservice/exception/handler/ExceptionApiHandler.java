package com.example.driveservice.exception.handler;

import com.example.driveservice.exception.EntityNotFoundException;
import com.example.driveservice.exception.JwtTokenException;
import com.example.driveservice.exception.MinIOException;
import com.example.driveservice.model.dto.response.ErrorResponse;
import com.example.driveservice.util.ExceptionFactory;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;
import java.util.Date;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionApiHandler{

    private final ExceptionFactory exceptionFactory;

    @ExceptionHandler({JwtTokenException.class})
    public ResponseEntity<?> handleJwtTokenException(JwtTokenException exception, HttpServletRequest request){
        ErrorResponse response =  exceptionFactory.getErrorResponse(exception, request.getServletPath(), HttpStatus.FORBIDDEN, LocalDateTime.now());
        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler({InvalidKeySpecException.class})
    public ResponseEntity<?> handleInvalidKeySpecException(InvalidKeySpecException exception, HttpServletRequest request){
        ErrorResponse response = exceptionFactory.getErrorResponse(exception, request.getServletPath(), HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException exception, HttpServletRequest request){
        ErrorResponse response = exceptionFactory.getErrorResponse(exception, request.getServletPath(), HttpStatus.NOT_FOUND, LocalDateTime.now());
        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler({MinIOException.class})
    public ResponseEntity<?> handleMinioException(MinIOException exception, HttpServletRequest request){
        ErrorResponse response = exceptionFactory.getErrorResponse(exception, request.getServletPath(), HttpStatus.SERVICE_UNAVAILABLE, LocalDateTime.now());
        return new ResponseEntity<>(response, response.getStatus());
    }
}
