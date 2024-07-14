package com.bandeira.reservation.services;

import com.bandeira.reservation.dtos.DadosEmail;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public DadosEmail emailPurchase(String text){
        DadosEmail dadosEmail1 = new DadosEmail(
                "Aluguel Cars Pedro",
                "pedro.amp002@gmail.com",
                "pedroamp1900@gmail.com",
                "We have received your reservation",
                text
        );
        return dadosEmail1;
    }
}
