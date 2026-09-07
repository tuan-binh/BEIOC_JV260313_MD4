package org.example.orderservice.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.example.orderservice.models.dto.wrapper.DataErr;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ngoại lệ về validation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> validException(HttpServletRequest request,MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getFieldErrors().forEach(
                err -> errors.put(err.getField(),err.getDefaultMessage())
        );

        return ResponseEntity.badRequest().body(
                DataErr.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .error(errors)
                        .message("Data failed")
                        .path(request.getRequestURI())
                        .build()
        );
    }
    // ngoại lệ về resource not found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(HttpServletRequest request,ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                DataErr.builder()
                        .status(HttpStatus.NOT_FOUND)
                        .error(ex.getMessage())
                        .message("Data failed")
                        .path(request.getRequestURI())
                        .build()
        );
    }

    // Ngoại lệ về lỗi server 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> exception(HttpServletRequest request,Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                DataErr.builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .error(ex.getMessage())
                        .message("Data failed")
                        .path(request.getRequestURI())
                        .build()
        );
    }

}
