package com.example.rabbit.acquisition.domain.entity;

import lombok.Data;

import java.util.List;

@Data
public class Purchase {

    private List<Product> products;
    private Long totalPrice;
    private String buyerName;

}
