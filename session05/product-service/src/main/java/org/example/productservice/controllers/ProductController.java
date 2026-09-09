package org.example.productservice.controllers;

import lombok.RequiredArgsConstructor;
import org.example.productservice.clients.UserClient;
import org.example.productservice.models.entities.Product;
import org.example.productservice.models.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final UserClient userClient;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {

        // Tự động trả về kết quả sau khi call API chéo dịch vụ với nhau
        userClient.getAll();

        return ResponseEntity.ok(productService.findById(id));
    }
}
