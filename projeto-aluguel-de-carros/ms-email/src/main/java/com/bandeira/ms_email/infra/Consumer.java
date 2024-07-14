package com.bandeira.ms_email.infra;

import com.bandeira.ms_email.dtos.EmailRequest;
import com.bandeira.ms_email.service.EmailService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    @Autowired
    private EmailService emailService;

    @RabbitListener(queues = "${mq.queues.sending-emails}")
    public void sendEmail(@Payload String payload) {
        try {
            var mapper = new ObjectMapper();
            var dados = mapper.readValue(payload, EmailRequest.class);
            emailService.sendEmail(dados);
        }catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
