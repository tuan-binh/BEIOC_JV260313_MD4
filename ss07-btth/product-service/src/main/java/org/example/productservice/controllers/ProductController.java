package org.example.productservice.controllers;

import lombok.RequiredArgsConstructor;
import org.example.productservice.models.dto.ProductDTO;
import org.example.productservice.models.entities.Product;
import org.example.productservice.models.services.IProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final IProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAll() {
        return ResponseEntity.ok(productService.findAll());
    }

    @PostMapping
    public ResponseEntity<ProductDTO> create(@RequestBody Product product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                productService.saveNewProduct(product)
        );
    }

}
