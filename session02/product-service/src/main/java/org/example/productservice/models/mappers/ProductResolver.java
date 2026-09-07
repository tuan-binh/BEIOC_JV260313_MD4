package org.example.productservice.models.mappers;

import lombok.RequiredArgsConstructor;
import org.example.productservice.models.entities.Category;
import org.example.productservice.models.repositories.ICategoryRepository;
import org.example.productservice.models.repositories.IProductRepository;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductResolver {
    private final ICategoryRepository categoryRepository;

    @Named("categoryFromId")
    public Category categoryFromId(Long id) {
        if(id == null) return null;

        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category Not Found"));
    }
    // update
    // có id -> tìm ra thông tin
    // if else logic nghiệp vụ

}
