package com.example.rqchallenge.employees.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestRepositoryExceptionHandler {
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity handleApplicationException(ServiceException e) {
        return ResponseEntity.status(e.getCustomError().getCode()).body(e.getCustomError());
    }

}
