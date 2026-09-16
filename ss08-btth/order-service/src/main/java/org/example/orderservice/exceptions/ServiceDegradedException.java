package org.example.orderservice.exceptions;

public class ServiceDegradedException extends RuntimeException {
    public ServiceDegradedException(String message) {
        super(message);
    }
}
