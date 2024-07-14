package com.bandeira.users.controllers;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.bandeira.users.dto.UpdateUserDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bandeira.users.dto.UserRequest;
import com.bandeira.users.model.User;
import com.bandeira.users.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping
    public ResponseEntity<UserRequest> registerUser(@RequestBody @Valid  UserRequest userRequest) throws JsonProcessingException {
        var response = userService.createUser(userRequest);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/verify")
    public String verifyUser(@Param("code") String code){
        if(userService.verify(code)){
            return "verify_success";
        } else {
            return "verify_fail";
        }
    }

    @GetMapping()
    public ResponseEntity<User> findByEmail(@RequestParam @Param("email") String email){
    	User obj = userService.findByEmail(email) ;
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping("updateUsername")
    public ResponseEntity<String> updateUsername(@PathVariable Long id, @RequestBody String name){
        userService.updateUserName(id, name);
        return ResponseEntity.ok().build();
    }

    @PostMapping("updateEmail")
    public ResponseEntity<String> updateEmail(@PathVariable Long id,@RequestBody String password, @RequestBody String email) {
        userService.updateEmail(id, password, email);
        return ResponseEntity.ok().build();
    }

    @PostMapping("updatePassword")
    public ResponseEntity<String> updatePassword(@PathVariable Long id, @RequestBody String password,
                                                 String newPassword, @RequestBody String passwordConfirmation) {
        userService.updatePassword(id, password, newPassword, passwordConfirmation);
        return ResponseEntity.ok().build();
    }

    @PostMapping("updateUser")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody UpdateUserDTO updateUserDTO) {
        userService.updateUser(id, updateUserDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/todos")
    private List<User> getAll(){
        return userService.findAll();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        userService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
