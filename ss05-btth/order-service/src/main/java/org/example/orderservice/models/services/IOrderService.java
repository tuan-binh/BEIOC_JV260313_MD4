package org.example.orderservice.models.services;

import org.example.orderservice.models.dto.req.OrderReq;
import org.example.orderservice.models.entities.Order;

public interface IOrderService {

    Order createOrder(OrderReq req);

}
