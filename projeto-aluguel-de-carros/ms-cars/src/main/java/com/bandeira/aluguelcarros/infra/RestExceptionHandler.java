package com.bandeira.aluguelcarros.infra;

import com.bandeira.aluguelcarros.exceptions.CarNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CarNotFoundException.class)
    private ResponseEntity<String> eventNotFoundHandler(CarNotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Carro não encontrado.");
    }
}
