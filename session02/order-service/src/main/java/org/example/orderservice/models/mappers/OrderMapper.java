package org.example.orderservice.models.mappers;

import org.example.orderservice.models.dto.req.CreateOrderRequest;
import org.example.orderservice.models.dto.res.OrderResponse;
import org.example.orderservice.models.entities.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    // request -> entity
    Order toEntity(CreateOrderRequest req);

    // entity -> response
    OrderResponse toResponse(Order order);

}
