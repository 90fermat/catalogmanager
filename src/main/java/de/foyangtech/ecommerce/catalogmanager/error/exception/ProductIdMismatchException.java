package de.foyangtech.ecommerce.catalogmanager.error.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductIdMismatchException extends RuntimeException {

    private ProductIdMismatchException (String message, Throwable cause)  {

        super(message, cause);
    }
    public ProductIdMismatchException() {

        super();
    }
}
