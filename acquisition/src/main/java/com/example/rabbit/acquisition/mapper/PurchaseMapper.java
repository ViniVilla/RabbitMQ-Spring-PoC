package com.example.rabbit.acquisition.mapper;

import com.example.rabbit.acquisition.domain.entity.Purchase;
import com.example.rabbit.acquisition.domain.request.PurchaseRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = ProductMapper.class)
public interface PurchaseMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "totalPrice", ignore = true),
            @Mapping(target = "status", ignore = true)
    })
    Purchase requestToEntity(PurchaseRequest request);

}
