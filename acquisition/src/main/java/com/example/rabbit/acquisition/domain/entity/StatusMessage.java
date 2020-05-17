package com.example.rabbit.acquisition.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatusMessage {

    private Long purchaseId;

    private PurchaseStatusEnum status;

}
