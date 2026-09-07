package org.example.categoryservice.exceptions;

public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException(Long id) {
        super("Không tìm thấy danh mục có id: " + id);
    }
}
