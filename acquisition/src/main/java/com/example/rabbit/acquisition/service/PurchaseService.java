package com.example.rabbit.acquisition.service;

import com.example.rabbit.acquisition.domain.entity.PaymentMessage;
import com.example.rabbit.acquisition.domain.entity.Product;
import com.example.rabbit.acquisition.domain.entity.Purchase;
import com.example.rabbit.acquisition.domain.entity.PurchaseStatusEnum;
import com.example.rabbit.acquisition.domain.entity.StatusMessage;
import com.example.rabbit.acquisition.domain.request.PurchaseRequest;
import com.example.rabbit.acquisition.mapper.PurchaseMapper;
import com.example.rabbit.acquisition.repository.PurchaseRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Log4j2
public class PurchaseService {

    private final ObjectMapper objectMapper;
    private final RabbitTemplate rabbitTemplate;
    private final PurchaseMapper purchaseMapper;
    private final PurchaseRepository purchaseRepository;

    @Autowired
    public PurchaseService(ObjectMapper objectMapper, RabbitTemplate rabbitTemplate, PurchaseMapper purchaseMapper, PurchaseRepository purchaseRepository) {
        this.objectMapper = objectMapper;
        this.rabbitTemplate = rabbitTemplate;
        this.purchaseMapper = purchaseMapper;
        this.purchaseRepository = purchaseRepository;
    }

    @Transactional
    public void createPurchase(PurchaseRequest request) {
        log.info("m=createPurchase, request={}", request);

        Purchase purchase = purchaseMapper.requestToEntity(request);
        purchase.setTotalPrice(calculateTotalPrice(purchase));
        purchase.setStatus(PurchaseStatusEnum.WAITING_FOR_PAYMENT);

        purchaseRepository.save(purchase);

        try{
            PaymentMessage message = new PaymentMessage(purchase.getId(), purchase.getTotalPrice());
            rabbitTemplate.convertAndSend("payment_processing_queue", objectMapper.writeValueAsString(message));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = {"payment_status_queue"})
    public void updatePurchaseStatus(@Payload String body) {
        try {
            StatusMessage message = objectMapper.readValue(body, StatusMessage.class);
            log.info("m=updatePurchaseStatus, body={}, message={}", body, message);

            Purchase purchase = purchaseRepository.findById(message.getPurchaseId()).orElseThrow();
            purchase.setStatus(message.getStatus());

            purchaseRepository.save(purchase);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private Long calculateTotalPrice(Purchase purchase) {
        return purchase.getProducts().stream()
                .mapToLong(Product::getPrice)
                .sum();

    }
}
