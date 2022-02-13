package com.example.assignment.utils;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GenericExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GenericExceptionHandler.class);

    @ExceptionHandler(value = { PersonNotFoundException.class })
    public ResponseEntity<Object> handleInvalidInputException(PersonNotFoundException ex) {
        log.error("Invalid Input Exception: " + ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { RuntimeException.class })
    public ResponseEntity<Object> handleInvalidInputException(RuntimeException ex) {
        log.error("Invalid Input Exception: " + ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { InvalidFormatException.class })
    public ResponseEntity<Object> handleInvalidInputException(InvalidFormatException ex) {
        log.error("Invalid role.");
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { Exception.class })
    public ResponseEntity<Object> handleInvalidInputException(Exception ex) {
        log.error("Invalid Input Exception: " + ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
