package com.bandeira.users.exceptions;

public class EmailAlreadyExistsException extends UserNotFoundException{


    public  EmailAlreadyExistsException(){
        super("This email already exists");
    }

    public EmailAlreadyExistsException(String message){
        super(message);
    }
}
