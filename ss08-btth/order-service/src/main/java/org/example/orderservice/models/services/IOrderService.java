package org.example.orderservice.models.services;

import org.example.orderservice.models.dto.req.CreateOrderRequest;
import org.example.orderservice.models.dto.res.OrderResponse;

import java.util.List;

public interface IOrderService {
    List<OrderResponse> findAll();

    OrderResponse create(CreateOrderRequest request);
}
