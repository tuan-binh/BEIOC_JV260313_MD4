package org.example.productservice.models.services.impl;

import lombok.RequiredArgsConstructor;
import org.example.productservice.clients.CategoryClient;
import org.example.productservice.models.dto.CategoryDTO;
import org.example.productservice.models.dto.ProductDTO;
import org.example.productservice.models.entities.Product;
import org.example.productservice.models.repositories.IProductRepository;
import org.example.productservice.models.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {
    private final IProductRepository productRepository;
    private final CategoryClient categoryClient;

    @Override
    public List<ProductDTO> findAll() {
        return productRepository.findAll().stream().map(
                p -> ProductDTO.builder()
                        .id(p.getId())
                        .name(p.getName())
                        .price(p.getPrice())
                        .stock(p.getStock())
                        .category(categoryClient.findById(p.getCategoryId()))
                        .build()
        ).toList();
    }

    @Override
    public ProductDTO saveNewProduct(Product product) {

        CategoryDTO categoryDTO = categoryClient.findById(product.getCategoryId());

        if(categoryDTO == null){
            throw new RuntimeException("Category Not Found");
        }

        product = productRepository.save(product);

        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .stock(product.getStock())
                .category(categoryDTO)
                .build();
    }

//    private CategoryDTO getCategoryById(Long id) {
//        return categoryClient.findById(id);
//    }

}
