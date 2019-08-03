package de.foyangtech.ecommerce.catalogmanager.error.exception;

public class ProductIdMismatchException extends RuntimeException {

    private ProductIdMismatchException (String message, Throwable cause)  {

        super(message, cause);
    }
    public ProductIdMismatchException() {

        super();
    }
}
