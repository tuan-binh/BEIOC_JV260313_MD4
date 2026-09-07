package org.example.productservice.controllers;

import lombok.RequiredArgsConstructor;
import org.example.productservice.models.dto.ProductDTO;
import org.example.productservice.models.entities.Product;
import org.example.productservice.models.services.IProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final IProductService productService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Product Product) {
        return ResponseEntity.ok(productService.save(Product));
    }

}
