package org.example.categoryservice.models.services.impl;

import lombok.RequiredArgsConstructor;
import org.example.categoryservice.models.entities.Category;
import org.example.categoryservice.models.repositories.ICategoryRepository;
import org.example.categoryservice.models.services.ICategoryService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements ICategoryService {
    private final ICategoryRepository categoryRepository;

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
    }
}
