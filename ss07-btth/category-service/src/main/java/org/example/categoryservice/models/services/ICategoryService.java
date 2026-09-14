package org.example.categoryservice.models.services;

import org.example.categoryservice.models.entities.Category;

public interface ICategoryService {
    Category findById(Long id);
}
