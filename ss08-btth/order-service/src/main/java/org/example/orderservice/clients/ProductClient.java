package org.example.orderservice.clients;

import org.example.orderservice.models.dto.clients.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service", path = "/api/v1/products")
public interface ProductClient {
    @GetMapping("/{id}")
    ProductDTO findById(@PathVariable Long id);
}
