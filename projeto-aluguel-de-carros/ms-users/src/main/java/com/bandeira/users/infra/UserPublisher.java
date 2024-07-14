package com.bandeira.users.infra;

import com.bandeira.users.dto.DadosEmail;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserPublisher {

    private final RabbitTemplate rabbitTemplate;

    private final Queue queueSendingEmail;

    public void sendingEmail(DadosEmail dados) throws JsonProcessingException {
        var json = convertDadosEmail(dados);
        rabbitTemplate.convertAndSend(queueSendingEmail.getName(), json);
    }

    private String convertDadosEmail(DadosEmail dados) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        var json = mapper.writeValueAsString(dados);
        return  json;
    }
}
