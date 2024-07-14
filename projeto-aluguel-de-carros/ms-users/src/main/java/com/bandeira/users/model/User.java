package com.bandeira.users.model;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Getter
@Setter
@Entity(name = "users")
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String email;

	private String password;

	private String cel;

	private String cpf;

	private LocalDate dateOfBirth;

	private Gender gender;

	private String verificationCode;

	private boolean enabled;

	private UserRole userRole;

	public User() {
	}


	public User(String name, String email, String password, String cel, String cpf, LocalDate dateOfBirth,
				Gender gender) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.cel = cel;
		this.cpf = cpf;
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
	}
}

