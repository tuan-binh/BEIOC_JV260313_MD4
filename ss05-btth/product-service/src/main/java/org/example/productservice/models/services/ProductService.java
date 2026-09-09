package org.example.productservice.models.services;

import org.example.productservice.models.entities.Product;

import java.util.List;

public interface ProductService {

    List<Product> findAll();

    Product findById(Long id);

    Product changeProduct(Long productId, int newQuantity);
}
