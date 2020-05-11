package com.example.rabbit.acquisition.resource;

import com.example.rabbit.acquisition.domain.entity.Purchase;
import com.example.rabbit.acquisition.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/purchases")
public class PurchaseResource {

    private final PurchaseService purchaseService;

    @Autowired
    public PurchaseResource(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping
    public void createPurchase(@RequestBody Purchase purchase) {
        purchaseService.createPurchase(purchase);
    }

}
