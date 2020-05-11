package com.example.rabbit.payment.service;

import com.example.rabbit.payment.domain.entity.Purchase;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class PaymentService {

    private final RabbitTemplate rabbitTemplate;

    private final ObjectMapper objectMapper;

    @Autowired
    public PaymentService(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = {"payment_processing_queue"})
    public void processPayment(@Payload String body) {
        Purchase purchase = null;
        try {
            purchase = objectMapper.readValue(body, Purchase.class);
            log.info("m=processPayment, body={}, purchase={}", body, purchase);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
