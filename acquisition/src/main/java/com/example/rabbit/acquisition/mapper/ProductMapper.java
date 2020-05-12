package com.example.rabbit.acquisition.mapper;

import com.example.rabbit.acquisition.domain.entity.Product;
import com.example.rabbit.acquisition.domain.request.ProductRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true)
    })
    Product requestToEntity(ProductRequest request);

}
