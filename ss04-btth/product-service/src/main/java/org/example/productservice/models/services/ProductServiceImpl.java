package org.example.productservice.models.services;

import lombok.RequiredArgsConstructor;
import org.example.productservice.models.dto.CategoryDTO;
import org.example.productservice.models.dto.ProductDTO;
import org.example.productservice.models.entities.Product;
import org.example.productservice.models.repositories.IProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {
    private final IProductRepository productRepository;
    private final RestTemplate restTemplate;

    @Override
    public List<ProductDTO> findAll() {
        return productRepository.findAll().stream().map(
                p -> {
                    return ProductDTO.builder()
                            .id(p.getId())
                            .name(p.getName())
                            .price(p.getPrice())
                            .stock(p.getStock())
                            .category(findById(p.getCategoryId()))
                            .build();
                }
        ).toList();
    }

    @Override
    public ProductDTO save(Product product) {

        CategoryDTO categoryDTO = findById(product.getCategoryId());
        if(categoryDTO == null) {
            throw new RuntimeException("Category not found");
        }

        product = productRepository.save(product);
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .stock(product.getStock())
                .category(findById(product.getCategoryId()))
                .build();
    }

    public CategoryDTO findById(Long id) {
        String url = "http://category-service/api/categories/" + id;
        return restTemplate.getForObject(url, CategoryDTO.class);
    }
}
