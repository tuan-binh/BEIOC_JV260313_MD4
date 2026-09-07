package org.example.productservice.controllers;

import org.example.productservice.models.services.ProductService;
import org.example.productservice.models.services.ProductService.PriceResult;
import org.example.productservice.models.services.ProductService.StockResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{productId}/price")
    public ResponseEntity<PriceResult> getPrice(@PathVariable Long productId) {
        return ResponseEntity.ok(productService.getPrice(productId));
    }

    @GetMapping("/{productId}/stock")
    public ResponseEntity<StockResult> checkStock(
            @PathVariable Long productId,
            @RequestParam(defaultValue = "1") int quantity
    ) {
        return ResponseEntity.ok(productService.checkStock(productId, quantity));
    }
}
