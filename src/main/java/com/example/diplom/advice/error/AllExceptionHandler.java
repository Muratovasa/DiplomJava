package com.example.diplom.advice.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AllExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<Violation> hamdlerException (ValidationException validationException){
        Violation violation = new Violation();
        violation.setMessage(validationException.getMessage());
        return new ResponseEntity<>(violation, HttpStatus.BAD_REQUEST);
    }
}
