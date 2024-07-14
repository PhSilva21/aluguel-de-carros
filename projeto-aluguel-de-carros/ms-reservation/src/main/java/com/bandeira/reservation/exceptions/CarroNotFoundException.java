package com.bandeira.reservation.exceptions;

public class CarroNotFoundException extends RuntimeException {

    public CarroNotFoundException(){
        super("Car not found");
    }

    public CarroNotFoundException(String message){
        super(message);
    }
}
