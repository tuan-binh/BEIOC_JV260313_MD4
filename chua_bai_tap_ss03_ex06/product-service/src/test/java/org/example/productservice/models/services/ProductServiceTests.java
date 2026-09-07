package org.example.productservice.models.services;

import org.example.productservice.models.entities.Product;
import org.example.productservice.models.repositories.ProductRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProductServiceTests {

    private final ProductRepository repository = mock(ProductRepository.class);
    private final ProductService service = new ProductService(repository);

    @Test
    void returnsProductPrice() {
        Product product = new Product("Laptop", new BigDecimal("15000000.00"), 5);
        when(repository.findById(1L)).thenReturn(Optional.of(product));

        ProductService.PriceResult result = service.getPrice(1L);

        assertEquals("Laptop", result.productName());
        assertEquals(new BigDecimal("15000000.00"), result.price());
    }

    @Test
    void reportsWhetherRequestedQuantityIsAvailable() {
        Product product = new Product("Laptop", new BigDecimal("15000000.00"), 5);
        when(repository.findById(1L)).thenReturn(Optional.of(product));

        assertTrue(service.checkStock(1L, 5).inStock());
        assertFalse(service.checkStock(1L, 6).inStock());
    }

    @Test
    void rejectsInvalidQuantity() {
        assertThrows(IllegalArgumentException.class, () -> service.checkStock(1L, 0));
    }

    @Test
    void throwsWhenProductDoesNotExist() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> service.getPrice(99L));
    }
}
