package org.example.orderservice.clients;

import org.example.orderservice.models.dto.client.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "product-service", path = "/api/v1/products")
public interface ProductClient {

    @GetMapping
    List<ProductDTO> findAll();

    @GetMapping("/{id}")
    ProductDTO findById(@PathVariable Long id);

    @PutMapping("/{id}")
    ProductDTO changeProduct(@PathVariable Long id, @RequestParam(name = "newQuantity", defaultValue = "0") int newQuantity);

}
