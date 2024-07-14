package com.bandeira.ms_email.service;

import com.bandeira.ms_email.dtos.EmailRequest;
import com.bandeira.ms_email.model.Email;
import com.bandeira.ms_email.repositories.EmailRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @InjectMocks
    EmailService emailService;

    @Mock
    EmailRepository emailRepository;

    @Mock
    JavaMailSender javaMailSender;

    @Captor
    ArgumentCaptor<Email> emailArgumentCaptor;

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    Email email = new Email(
            UUID.randomUUID(),
            "Aluguel cars",
            "alguelcars@gmail.com",
            "client@gmail.com",
            "Verify account",
            "sasahsasyagsatfstasa",
            LocalDateTime.of(2024,07,03,12,02,12)
    );

    EmailRequest emailRequest = new EmailRequest(
            "Hotel",
            "hotel@gmail.com",
            "clientdois@gmail.com",
            "Sua reserva foi feita",
            "asyagydgwftdfwwvw"
    );


    @Nested
    class sendEmail {

        @Test
        @DisplayName("Must send email successfully")
        void MustSendEmailSuccessfully() {
            doReturn(email)
                    .when(emailRepository)
                    .save(emailArgumentCaptor.capture());

            var response = emailService.sendEmail(emailRequest);

            assertNotNull(response);

            var emailCaptured = emailArgumentCaptor.getValue();

            assertEquals(emailRequest.ownerRef(), emailCaptured.getOwnerRef());
            assertEquals(emailRequest.emailFrom(), emailCaptured.getEmailFrom());
            assertEquals(emailRequest.emailTo(), emailCaptured.getEmailTo());
            assertEquals(emailRequest.subject(), emailCaptured.getSubject());
            assertEquals(emailRequest.text(), emailCaptured.getText());
        }

        @Test
        @DisplayName("Must return exception when sending email")
        void MustReturnExceptionWhenSendingEmail(){
            doThrow(new RuntimeException()).when(emailRepository).save(any());

            assertThrows(RuntimeException.class,
                    () -> emailService.sendEmail(emailRequest));
        }
    }

    @Nested
    class findAll {

        @Test
        @DisplayName("Must return all emails")
        void MustReturnAllEmails() {
            var emailList = List.of(email);

            doReturn(emailList).when(emailRepository).findAll();

            var response = emailService.findAll();

            assertEquals(emailList.size(), response.size());
        }
    }

    @Nested
    class findById {

        @Test
        @DisplayName("Must return email successfully")
        void MustReturnEmailSuccessfully() {
            doReturn(Optional.of(email))
                    .when(emailRepository)
                    .findById(uuidArgumentCaptor.capture());

            var response = emailService.findById(email.getId().toString());

            assertTrue(response.isPresent());
            assertEquals(email.getId(), uuidArgumentCaptor.getValue());
        }

        @Test
        @DisplayName("Should fail to return email")
        void ShouldFailToReturnEmail(){
            doReturn(Optional.empty())
                    .when(emailRepository)
                    .findById(uuidArgumentCaptor.capture());

            var response = emailService.findById(email.getId().toString());

            assertNotNull(response);
        }
    }
}