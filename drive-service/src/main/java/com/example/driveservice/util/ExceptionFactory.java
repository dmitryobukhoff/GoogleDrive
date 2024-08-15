package com.example.driveservice.util;

import com.example.driveservice.model.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
public class ExceptionFactory {

    public ErrorResponse getErrorResponse(Exception exception, String path, HttpStatus status, LocalDateTime timestamp){
        return ErrorResponse.builder()
                .errorPath(path)
                .description(exception.getMessage())
                .timestamp(timestamp)
                .status(status)
                .build();
    }
}
