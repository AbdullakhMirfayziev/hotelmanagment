package com.example.hotelmanagment.exceptions;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
public class ExceptionsHandler {
    private static final Logger logger  = LoggerFactory.getLogger(ExceptionsHandler.class);

//    @ResponseBody(HttpStatus.FORBIDDEN)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        logger.error("403 error occurred: " + e.getMessage());

        return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
    }
}
