package org.example.productservice.controllers;

import lombok.RequiredArgsConstructor;
import org.example.productservice.models.entities.Product;
import org.example.productservice.models.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestParam(name = "newQuantity", defaultValue = "0") int newQuantity) {
        return ResponseEntity.ok(productService.changeProduct(id, newQuantity));
    }
}
