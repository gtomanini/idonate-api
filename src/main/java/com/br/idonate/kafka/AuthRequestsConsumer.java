package com.br.idonate.kafka;


import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Configuration
public class AuthRequestsConsumer {

    @KafkaListener(
            topics = "fromAuthController",
            groupId = "grupo1")
    public void consumeMessage(String message) {
        System.out.println("Mensagem recebida=========\n" + message);
    }
}
