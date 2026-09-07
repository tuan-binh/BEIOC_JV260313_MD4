package org.example.productservice.models.services;

import org.example.productservice.models.dto.req.ProductReq;
import org.example.productservice.models.entities.Product;

import java.util.List;

public interface IProductService {

    List<Product> findAll();

    Product addNewProduct(ProductReq req);

}
