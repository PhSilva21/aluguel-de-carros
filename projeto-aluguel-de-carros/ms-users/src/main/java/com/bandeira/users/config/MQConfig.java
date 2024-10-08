package com.bandeira.users.config;


import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {

    @Value("${mq.queues.sending-emails}")
    private String sendingEmailQueue;

    @Bean
    public Queue queueSendingEmail(){
        return new Queue( sendingEmailQueue, true);
    }
}
