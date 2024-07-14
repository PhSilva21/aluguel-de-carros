package com.bandeira.users.dto;

import com.bandeira.users.model.Gender;

import java.time.LocalDate;

public record UpdateUserDTO(

        String name,

        String cel,

        String cpf,

        LocalDate dateOfBirth,

        Gender gender

) {
}
