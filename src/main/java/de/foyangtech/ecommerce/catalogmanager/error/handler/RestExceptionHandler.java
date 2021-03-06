package de.foyangtech.ecommerce.catalogmanager.error.handler;

import de.foyangtech.ecommerce.catalogmanager.error.exception.ProductIdMismatchException;
import de.foyangtech.ecommerce.catalogmanager.error.exception.ProductNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class RestExceptionHandler  extends ResponseEntityExceptionHandler {


    @ExceptionHandler({ProductNotFoundException.class})
    protected ResponseEntity<Object> handleNotfound(
            Exception ex, WebRequest request) {
        return  handleExceptionInternal(ex, "product not found",
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({ProductIdMismatchException.class,
            ConstraintViolationException.class,
            DataIntegrityViolationException.class,
             NullPointerException.class})
    public ResponseEntity<Object> handleBadRequest(
            Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getLocalizedMessage() + "\n Please try again",
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }



}
