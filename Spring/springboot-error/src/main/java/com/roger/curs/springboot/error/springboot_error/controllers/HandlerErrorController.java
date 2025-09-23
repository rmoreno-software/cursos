package com.roger.curs.springboot.error.springboot_error.controllers;

import com.roger.curs.springboot.error.springboot_error.models.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class HandlerErrorController {

    @ExceptionHandler(ArithmeticException.class)
    public ResponseEntity<com.roger.curs.springboot.error.springboot_error.models.Error> divisionByZero(Exception ex) {
        Error error = new Error();
        error.setDate(new Date());
        error.setError("Error divisió per cero!");
        error.setMessage(ex.getMessage());
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        // return ResponseEntity.internalServerError().body(error);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(error);
    }
}
