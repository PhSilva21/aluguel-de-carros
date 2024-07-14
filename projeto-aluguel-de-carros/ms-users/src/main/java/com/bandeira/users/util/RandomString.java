package com.bandeira.users.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class RandomString {

    private final String CHARACTERS = "abcdefghijklmnopqrstuvwxyz0123456789";

    public String generateRandomString(int length){
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++){
            int index = secureRandom.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        return sb.toString();
    }
}

