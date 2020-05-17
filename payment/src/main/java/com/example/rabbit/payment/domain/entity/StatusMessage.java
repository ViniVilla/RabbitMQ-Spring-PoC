package com.example.rabbit.payment.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatusMessage {

    private Long purchaseId;

    private PurchaseStatusEnum status;

}
