package com.example.driveservice.exception;

import com.example.driveservice.util.UserContext;

public abstract class CustomRuntimeException extends RuntimeException{
    public CustomRuntimeException(String message) {
        super(message);
        UserContext.clear();
    }

    public CustomRuntimeException(Throwable cause) {
        super(cause);
        UserContext.clear();
    }

    public CustomRuntimeException() {
        super();
        UserContext.clear();
    }

    public CustomRuntimeException(String message, Throwable cause) {
        super(message, cause);
        UserContext.clear();
    }

    protected CustomRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        UserContext.clear();
    }
}
