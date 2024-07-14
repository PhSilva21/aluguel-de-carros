package com.bandeira.reservation.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Client {

    private String name;

    private String email;

    private String password;

    private String cpf;

    private String verificationCode;

    private boolean enabled;

}
