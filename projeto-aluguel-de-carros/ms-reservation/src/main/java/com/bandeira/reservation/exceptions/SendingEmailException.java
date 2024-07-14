package com.bandeira.reservation.exceptions;

public class SendingEmailException extends RuntimeException{

    public SendingEmailException(){
        super("Error sending email");
    }

    public SendingEmailException(String message){
        super(message);
    }
}
