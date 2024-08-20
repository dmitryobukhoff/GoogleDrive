package com.example.driveservice.exception;

public class EntityNotFoundException extends CustomRuntimeException{
    public EntityNotFoundException(String message){
        super(message);
    }
}
