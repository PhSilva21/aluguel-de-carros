package com.bandeira.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bandeira.users.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);
	User findByVerificationCode(String verificationCode);
}
