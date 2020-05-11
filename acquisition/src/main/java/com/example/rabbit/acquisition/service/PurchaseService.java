package com.example.rabbit.acquisition.service;

import com.example.rabbit.acquisition.domain.entity.Purchase;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class PurchaseService {

    private final ObjectMapper objectMapper;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public PurchaseService(ObjectMapper objectMapper, RabbitTemplate rabbitTemplate) {
        this.objectMapper = objectMapper;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void createPurchase(Purchase purchase) {
        log.info("m=createPurchase, purchase={}", purchase);
        try{
            rabbitTemplate.convertAndSend("payment_processing_queue", objectMapper.writeValueAsString(purchase));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
