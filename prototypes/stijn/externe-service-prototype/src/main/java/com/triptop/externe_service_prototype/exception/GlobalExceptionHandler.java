package com.triptop.externe_service_prototype.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotImplementedException.class)
    public ResponseEntity<String> handleNotImplementedException(NotImplementedException e) {
        return ResponseEntity.status(501).body(e.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler(RequestFailedException.class)
    public ResponseEntity<String> handleRequestFailedException(RequestFailedException e) {
        return ResponseEntity.status(500).body(e.getMessage());
    }
}
