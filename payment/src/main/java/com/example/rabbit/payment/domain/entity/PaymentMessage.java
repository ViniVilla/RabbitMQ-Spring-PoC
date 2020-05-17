package com.example.rabbit.payment.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentMessage {

    private Long purchaseId;

    private Long totalPrice;

}