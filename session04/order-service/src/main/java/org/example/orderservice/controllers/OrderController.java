package org.example.orderservice.controllers;

import lombok.RequiredArgsConstructor;
import org.example.orderservice.models.dto.ProductDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final RestTemplate restTemplate;

    @GetMapping("/products")
    public ResponseEntity<?> getAllProduct() {
        // sử dụng với eureka service thì cần tên dịch vụ gọi đến
        // không cần phải khai báo localhost hay là port hoặc biết địa chỉ IP của service đó
        String url = "http://product-service/api/products";

        ProductDTO[] products = restTemplate.getForObject(url, ProductDTO[].class);

        if (products != null && products.length > 0) {
            return ResponseEntity.ok(products);
        }

        return ResponseEntity.ok(new ArrayList<>());
    }

}
