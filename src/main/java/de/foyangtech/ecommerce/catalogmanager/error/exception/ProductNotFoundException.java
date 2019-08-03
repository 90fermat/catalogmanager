package de.foyangtech.ecommerce.catalogmanager.error.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductNotFoundException  extends RuntimeException {

    public ProductNotFoundException (int id) {

        super("We can't find any  product with the id: " + id);
    }
}
