package de.foyangtech.ecommerce.catalogmanager.error.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
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
