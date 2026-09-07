package org.example.orderservice.models.services.impl;

import lombok.RequiredArgsConstructor;
import org.example.orderservice.exceptions.ResourceNotFoundException;
import org.example.orderservice.models.constants.OrderStatus;
import org.example.orderservice.models.dto.req.CreateOrderRequest;
import org.example.orderservice.models.dto.res.OrderResponse;
import org.example.orderservice.models.entities.Order;
import org.example.orderservice.models.mappers.OrderMapper;
import org.example.orderservice.models.repositories.IOrderRepository;
import org.example.orderservice.models.services.IOrderService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements IOrderService {
    private final IOrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public OrderResponse createOrder(CreateOrderRequest req) {
        Order order = orderMapper.toEntity(req);
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
        order = orderRepository.save(order);
        return orderMapper.toResponse(order);
    }

    @Override
    public OrderResponse findById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        return orderMapper.toResponse(order);
    }
}
