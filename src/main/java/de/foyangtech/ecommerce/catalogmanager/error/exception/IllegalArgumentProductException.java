package de.foyangtech.ecommerce.catalogmanager.error.exception;

public class IllegalArgumentProductException extends RuntimeException {

    public IllegalArgumentProductException() {
    }

    public IllegalArgumentProductException(String message) {
        super(message);
    }

    public IllegalArgumentProductException(String message, Throwable cause) {
        super(message, cause);
    }
}
