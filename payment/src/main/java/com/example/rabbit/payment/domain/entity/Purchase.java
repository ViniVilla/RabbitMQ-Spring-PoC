package com.example.rabbit.payment.domain.entity;

import lombok.Data;

import java.util.List;

@Data
public class Purchase {

    private List<Product> products;
    private Long totalPrice;
    private String buyerName;

}
