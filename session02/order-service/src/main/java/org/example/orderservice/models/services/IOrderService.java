package org.example.orderservice.models.services;

import org.example.orderservice.models.dto.req.CreateOrderRequest;
import org.example.orderservice.models.dto.res.OrderResponse;

public interface IOrderService {

    OrderResponse createOrder(CreateOrderRequest req);

    OrderResponse findById(Long id);

}
