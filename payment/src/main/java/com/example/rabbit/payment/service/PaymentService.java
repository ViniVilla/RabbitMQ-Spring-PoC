package com.example.rabbit.payment.service;

import com.example.rabbit.payment.domain.entity.PaymentMessage;
import com.example.rabbit.payment.domain.entity.PurchaseStatusEnum;
import com.example.rabbit.payment.domain.entity.StatusMessage;
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

    private final ObjectMapper objectMapper;

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public PaymentService(ObjectMapper objectMapper, RabbitTemplate rabbitTemplate) {
        this.objectMapper = objectMapper;
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = {"payment_processing_queue"})
    public void processPayment(@Payload String body) {
        try {
            PaymentMessage message = objectMapper.readValue(body, PaymentMessage.class);
            log.info("m=processPayment, body={}, message={}", body, message);

            Thread.sleep(5000);
            sendStatusMessage(PurchaseStatusEnum.WAITING_FOR_CONFIRMATION, message.getPurchaseId());

            Thread.sleep(5000);
            sendStatusMessage(PurchaseStatusEnum.PAYED, message.getPurchaseId());

        } catch (JsonProcessingException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void sendStatusMessage(PurchaseStatusEnum status, long purchaseId) throws JsonProcessingException {
        log.info("m=sendStatusMessage, status={}, purchaseId={}", status, purchaseId);
        StatusMessage statusMessage = new StatusMessage(purchaseId, status);
        rabbitTemplate.convertAndSend("payment_status_queue", objectMapper.writeValueAsString(statusMessage));
    }

}
