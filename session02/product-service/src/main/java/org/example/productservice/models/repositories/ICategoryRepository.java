package org.example.productservice.models.repositories;

import org.example.productservice.models.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoryRepository extends JpaRepository<Category,Long> {
}
