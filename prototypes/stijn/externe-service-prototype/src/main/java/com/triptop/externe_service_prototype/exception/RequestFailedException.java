package com.triptop.externe_service_prototype.exception;

public class RequestFailedException extends RuntimeException {
    public RequestFailedException(String message) {
        super(message);
    }
}
