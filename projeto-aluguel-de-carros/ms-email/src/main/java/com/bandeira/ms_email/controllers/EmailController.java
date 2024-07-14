package com.bandeira.ms_email.controllers;

import com.bandeira.ms_email.dtos.EmailRequest;
import com.bandeira.ms_email.model.Email;
import com.bandeira.ms_email.service.EmailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "emails")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/sending")
    public ResponseEntity<Email> sendingEmailManually(@RequestBody @Valid EmailRequest emailRequest) {
        var email = emailService.sendEmail(emailRequest);
        return new ResponseEntity<>(email, HttpStatus.CREATED);
    }
}

