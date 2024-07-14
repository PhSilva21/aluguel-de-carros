package com.bandeira.users.services;

import com.bandeira.users.dto.DadosEmail;
import com.bandeira.users.dto.UserRequest;
import com.bandeira.users.model.User;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public DadosEmail emailPurchase(UserRequest userRequest, String text){
        DadosEmail dadosEmail1 = new DadosEmail(
                "Aluguel Cars Pedro",
                "pedro.amp002@gmail.com",
                userRequest.email(),
                "Please check your email",
                null
        );
        return dadosEmail1;
    }
}
