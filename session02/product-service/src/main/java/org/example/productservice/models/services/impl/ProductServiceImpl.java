package org.example.productservice.models.services.impl;

import lombok.RequiredArgsConstructor;
import org.example.productservice.models.dto.req.ProductReq;
import org.example.productservice.models.entities.Product;
import org.example.productservice.models.mappers.ProductMapper;
import org.example.productservice.models.repositories.IProductRepository;
import org.example.productservice.models.services.IProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {
    private final IProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product addNewProduct(ProductReq req) {
        Product product = productMapper.toEntity(req);
        return productRepository.save(product);
    }
}
