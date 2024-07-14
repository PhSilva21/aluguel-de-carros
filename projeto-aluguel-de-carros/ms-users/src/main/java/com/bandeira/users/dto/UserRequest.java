package com.bandeira.users.dto;

import com.bandeira.users.model.Gender;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UserRequest(

		@NotNull(message = "O nome não pode ser nulo")
		@NotBlank(message = "O nome não pode ser vazio")
		String name,

		@NotNull(message = "O email não pode ser nulo")
		@NotBlank(message = "O email não pode ser vazio")
		@Email
		String email,

		@NotNull(message = "O email não pode ser nulo")
		@NotBlank(message = "O email não pode ser vazio")
		@Size(min = 8, message = "A senha deve conter no minimo 8 caracteres")
		String password,

		String cel,

		String cpf,

		LocalDate dateOfBirth,

		Gender gender

) {

}


