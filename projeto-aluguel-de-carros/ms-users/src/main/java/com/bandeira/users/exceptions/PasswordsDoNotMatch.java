package com.bandeira.users.exceptions;

public class PasswordsDoNotMatch extends RuntimeException{

    public PasswordsDoNotMatch(){
        super("Passwords do not match");
    }

    public PasswordsDoNotMatch(String message){
        super(message);
    }
}
