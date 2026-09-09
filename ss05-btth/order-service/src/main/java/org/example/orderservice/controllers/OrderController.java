package org.example.orderservice.controllers;

import lombok.RequiredArgsConstructor;
import org.example.orderservice.models.dto.req.OrderReq;
import org.example.orderservice.models.services.IOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final IOrderService orderService;

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderReq req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(req));
    }
}
