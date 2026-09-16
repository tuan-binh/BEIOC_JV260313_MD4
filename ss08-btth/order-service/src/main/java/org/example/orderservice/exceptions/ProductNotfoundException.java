package org.example.orderservice.exceptions;

public class ProductNotfoundException extends RuntimeException {
    public ProductNotfoundException(String message) {
        super(message);
    }
}
