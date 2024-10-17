package com.example.hotelmanagment.exceptions;

import org.apache.coyote.BadRequestException;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ExceptionsHandler {
    private static final Logger logger  = LoggerFactory.getLogger(ExceptionsHandler.class);

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleException(AccessDeniedException e) {
        logger.error("403 error occurred: {}", e.getMessage());

        return new ResponseEntity<>("You aren't allowed for this action", HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
    public ResponseEntity<?> handleException(ChangeSetPersister.NotFoundException e) {
        logger.error("404 error occurred: {}", e.getMessage());

        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleException(BadRequestException e) {
        logger.error("400 error occurred: {}", e.getMessage());

        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RoomAlreadyBookedException.class)
    public ResponseEntity<?> handleException(RoomAlreadyBookedException e) {
        logger.error("409 error occurred: {}", e.getMessage());

        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        logger.error("500 error occurred: {}", e.getMessage());

        return new ResponseEntity<>("Ops, Something went wrong!!!", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
