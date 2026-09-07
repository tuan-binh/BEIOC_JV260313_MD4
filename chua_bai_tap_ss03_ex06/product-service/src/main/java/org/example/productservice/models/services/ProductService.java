package org.example.productservice.models.services;

import org.example.productservice.models.entities.Product;
import org.example.productservice.models.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public PriceResult getPrice(Long productId) {
        Product product = findProduct(productId);
        return new PriceResult(product.getId(), product.getName(), product.getPrice());
    }

    public StockResult checkStock(Long productId, int requestedQuantity) {
        if (requestedQuantity < 1) {
            throw new IllegalArgumentException("Số lượng cần kiểm tra phải lớn hơn 0");
        }

        Product product = findProduct(productId);
        int availableQuantity = product.getStockQuantity();
        return new StockResult(
                product.getId(),
                product.getName(),
                requestedQuantity,
                availableQuantity,
                availableQuantity >= requestedQuantity
        );
    }

    private Product findProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
    }

    public record PriceResult(Long productId, String productName, BigDecimal price) {
    }

    public record StockResult(
            Long productId,
            String productName,
            int requestedQuantity,
            int availableQuantity,
            boolean inStock
    ) {
    }
}
