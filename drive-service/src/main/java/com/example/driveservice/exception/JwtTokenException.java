package com.example.driveservice.exception;

public class JwtTokenException extends CustomRuntimeException{
    public JwtTokenException(String message) {
        super(message);
    }

    public JwtTokenException(Throwable cause) {
        super(cause);
    }
}
