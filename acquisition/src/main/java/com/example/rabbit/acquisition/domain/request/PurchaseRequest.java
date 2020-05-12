package com.example.rabbit.acquisition.domain.request;

import com.example.rabbit.acquisition.domain.entity.Product;
import lombok.Data;

import java.util.List;

@Data
public class PurchaseRequest {

    private List<Product> products;

    private String buyerName;

}
