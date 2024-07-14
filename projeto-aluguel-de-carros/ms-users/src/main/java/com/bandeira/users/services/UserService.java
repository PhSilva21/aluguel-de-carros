package com.bandeira.users.services;

import java.util.List;

import com.bandeira.users.dto.UpdateUserDTO;
import com.bandeira.users.dto.UserRequest;
import com.bandeira.users.exceptions.EmailAlreadyExistsException;
import com.bandeira.users.exceptions.IncorrectPasswordException;
import com.bandeira.users.exceptions.PasswordsDoNotMatch;
import com.bandeira.users.exceptions.UserNotFoundException;
import com.bandeira.users.infra.UserPublisher;
import com.bandeira.users.util.RandomString;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bandeira.users.model.User;
import com.bandeira.users.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;


	private final PasswordEncoder passwordEncoder;


	private final RandomString randomString;


	private final EmailService emailService;


	private final UserPublisher userPublisher;

	public UserRequest createUser(UserRequest userRequest) throws JsonProcessingException {
		if (userRepository.findByEmail(userRequest.email()) != null) {
			throw new EmailAlreadyExistsException();
		}else {
			
			User user = new User(
					userRequest.name(),
					userRequest.email(),
					userRequest.password(),
					userRequest.cel(),
					userRequest.cpf(),
					userRequest.dateOfBirth(),
					userRequest.gender()
			);

			String encodedPassword = passwordEncoder.encode(user.getPassword());
			user.setPassword(encodedPassword);

			String randomCode = randomString.generateRandomString(64);
			user.setVerificationCode(randomCode);
			user.setEnabled(false);

			var email = emailService.emailPurchase(userRequest, user.getName() +  ", Please check your email");

			userPublisher.sendingEmail(email);

			userRepository.save(user);



			return userRequest;
		}			
	}



	public boolean verify(String verificationCode) {

		var user = userRepository.findByVerificationCode(verificationCode);

		if (user == null || user.isEnabled()) {
			return false;
		} else {
			user.setVerificationCode(null);
			user.setEnabled(true);
			userRepository.save(user);

			return true;
		}
	}


	public User findByEmail(String email) {
		var user = userRepository.findByEmail(email);

		if (user == null) {
			throw new UserNotFoundException();
		} else
			return user;
	}


	public String updateUserName(Long id, String name) {

		var user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

		user.setName(name);

		userRepository.save(user);

		return "Username has been updated successfully";
	}


	public String updateEmail(Long id, String password,  String email) {

		var user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

		if(!password.equals(user.getPassword())){
			throw new IncorrectPasswordException();
		}

		user.setEmail(email);

		userRepository.save(user);

		return "Your email has been updated successfully";
	}


	public String updatePassword(Long id, String password, String newPassword, String passwordConfirmation) {

		var user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

		if (!user.getPassword().equals(password)){
			throw new IncorrectPasswordException();
		}

		if (!newPassword.equals(passwordConfirmation)){
			throw new PasswordsDoNotMatch();
		}

		user.setPassword(password);

		userRepository.save(user);

		return "Your password has been updated successfully";
	}

	public String updateUser(Long id, UpdateUserDTO updateUserDTO){

		var user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

		user.setName(updateUserDTO.name());
		user.setCel(updateUserDTO.cel());
		user.setCpf(updateUserDTO.cpf());
		user.setDateOfBirth(updateUserDTO.dateOfBirth());
		user.setGender(updateUserDTO.gender());

		userRepository.save(user);

		return "Your data has been successfully updated";
	}



	public List<User> findAll() {
		return (List<User>) userRepository.findAll();
	}

	public void deleteById(Long id){
		userRepository.findById(id).orElseThrow(UserNotFoundException::new);

		userRepository.deleteById(id);
	}


	
}
