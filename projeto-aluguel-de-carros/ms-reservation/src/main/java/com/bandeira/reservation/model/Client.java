package com.bandeira.reservation.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Client {

    private String name;

    private String email;

    private String password;

    private String cpf;

    private boolean enabled;

}
