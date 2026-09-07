package org.example.productservice.models.mappers;

import org.example.productservice.models.dto.req.ProductReq;
import org.example.productservice.models.entities.Category;
import org.example.productservice.models.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring",uses = ProductResolver.class)
public interface ProductMapper {

    // Nếu như source với target giống nhau thì ko cần viết cx được sẽ tự động
//    @Mapping(source = "name",target = "name")
//    @Mapping(source = "price",target = "price")
//    @Mapping(source = "stock",target = "stock")
    @Mapping(
            source = "categoryId",
            target = "category",
            qualifiedByName = "categoryFromId"
    )
    Product toEntity(ProductReq req);

    // Ở đây có thể tạo rất nhiều phương thức biến đổi
    // req -> entity
    // entity -> response
    // req -> response
//
//    @Named("categoryFromId")
//    default Category categoryFromId(Long id) {
//        if(id == null) return null;
//
//        Category category = new Category();
//        category.setId(id);
//
//        return category;
//    }

}
