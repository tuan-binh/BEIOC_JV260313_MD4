package org.example.productservice.models.services;

import org.example.productservice.models.entities.Product;

public interface IProductService {

    Product findById(Long id);

}
