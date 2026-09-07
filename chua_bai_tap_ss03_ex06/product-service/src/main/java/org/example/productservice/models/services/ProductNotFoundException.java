package org.example.productservice.models.services;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(Long productId) {
        super("Không tìm thấy sản phẩm có id: " + productId);
    }
}
