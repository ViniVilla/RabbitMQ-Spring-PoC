package com.example.rabbit.acquisition.domain.entity;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Entity
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(targetEntity = Product.class, cascade = CascadeType.ALL)
    private List<Product> products;

    private Long totalPrice;

    private String buyerName;

    @Enumerated(EnumType.STRING)
    private PurchaseStatusEnum status;

}
