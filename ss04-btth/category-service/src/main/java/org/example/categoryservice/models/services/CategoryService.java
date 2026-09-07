package org.example.categoryservice.models.services;

import lombok.RequiredArgsConstructor;
import org.example.categoryservice.exceptions.CategoryNotFoundException;
import org.example.categoryservice.models.entities.Category;
import org.example.categoryservice.models.repositories.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
    }
}
