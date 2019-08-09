package de.foyangtech.ecommerce.catalogmanager.service;

import de.foyangtech.ecommerce.catalogmanager.persistance.model.Product;
import org.springframework.security.core.parameters.P;

import java.util.List;

public interface IProductService {

    List<Product> getAllProducts();
    List<Product> addAllProducts(List<Product> products);
    Product getProductById(long productId);
    Product addProduct(Product product);
    void updateProduct(Product product);
    void deleteProduct(int ProductId);
}
