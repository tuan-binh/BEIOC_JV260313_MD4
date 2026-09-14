package org.example.productservice.clients;

import org.example.productservice.models.dto.CategoryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "category-service", path = "/api/v1/categories")
public interface CategoryClient {

    @GetMapping("/{id}")
    CategoryDTO findById(@PathVariable Long id);

}
