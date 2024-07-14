package com.bandeira.aluguelcarros.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class CarNotFoundException extends RuntimeException {

    public CarNotFoundException()  {
        super("Carro não encontrado");
    }

    public CarNotFoundException(String message) {
        super(message);
    }
}
