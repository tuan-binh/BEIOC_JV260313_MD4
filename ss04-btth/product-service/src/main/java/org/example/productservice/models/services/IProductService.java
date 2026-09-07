package org.example.productservice.models.services;

import org.example.productservice.models.dto.ProductDTO;
import org.example.productservice.models.entities.Product;

import java.util.List;

public interface IProductService {
    List<ProductDTO> findAll();

    ProductDTO save(Product product);
}
