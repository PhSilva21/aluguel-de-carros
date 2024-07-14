package com.bandeira.ms_email.service;

import com.bandeira.ms_email.dtos.EmailRequest;
import com.bandeira.ms_email.model.Email;
import com.bandeira.ms_email.model.StatusEmail;
import com.bandeira.ms_email.repositories.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmailService {

    @Autowired
    EmailRepository emailRepository;

    @Autowired
    private JavaMailSender emailSender;

    @Transactional
    public Email sendEmail(EmailRequest emailRequest) {


        Email email = new Email(
                UUID.randomUUID(),
                emailRequest.ownerRef(),
                emailRequest.emailFrom(),
                emailRequest.emailTo(),
                emailRequest.subject(),
                emailRequest.text(),
                LocalDateTime.now()
        );
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(email.getEmailFrom());
            message.setTo(email.getEmailTo());
            message.setSubject(email.getSubject());
            message.setText(email.getText());
            emailSender.send(message);

            email.setStatusEmail(StatusEmail.SENT);
        } catch (MailException e) {
            email.setStatusEmail(StatusEmail.ERROR);
        }
        return emailRepository.save(email);
    }


    public List<Email> findAll() {
        return  emailRepository.findAll();
    }

    public Optional<Email> findById(String emailId) {
        return emailRepository.findById(UUID.fromString(emailId));
    }
}
