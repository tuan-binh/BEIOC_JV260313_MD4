package com.example.orderservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final KafkaTemplate<String, Object> kafkaTemplate;


    @PostMapping
    public ResponseEntity<?> createOrder(@RequestParam(name = "email", defaultValue = "") String email) {
        // Hành động gửi message bất đồng bộ
        kafkaTemplate.send("order-created",1, email, email);

        return ResponseEntity.status(HttpStatus.CREATED).body(email + " Ordered Successfully");
    }


}
