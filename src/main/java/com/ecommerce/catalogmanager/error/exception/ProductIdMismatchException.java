package com.ecommerce.catalogmanager.error.exception;

public class ProductIdMismatchException extends RuntimeException {

    public ProductIdMismatchException(String message, Throwable cause) {

        super(message, cause);
    }
    public ProductIdMismatchException() {

        super();
    }
}
