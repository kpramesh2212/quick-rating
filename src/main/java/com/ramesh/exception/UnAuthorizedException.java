package com.ramesh.exception;

public class UnAuthorizedException extends RuntimeException {
    public UnAuthorizedException() {

    }

    public UnAuthorizedException(String message) {
        super(message);
    }

    public UnAuthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
}
