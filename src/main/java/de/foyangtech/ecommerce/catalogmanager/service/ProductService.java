package de.foyangtech.ecommerce.catalogmanager.service;

import de.foyangtech.ecommerce.catalogmanager.persistance.dao.ProductDao;
import de.foyangtech.ecommerce.catalogmanager.persistance.model.LastProductsAdded;
import de.foyangtech.ecommerce.catalogmanager.persistance.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService implements IProductService {

    @Autowired
    private ProductDao productDao;


    private LastProductsAdded lastAdded;

    public ProductService(LastProductsAdded lastAdded, ProductDao productDao) {
        this.lastAdded = lastAdded;
        this.productDao = productDao;
    }

    @Override
    public List<Product> getAllProducts() {
        return null;
    }

    @Override
    public Product getProductById(long productId) {
        return null;
    }

    @Override
    public Product addProduct(Product entity) {
        Product product = productDao.save(entity);
        lastAdded.addToLastest(entity);
        return product;
    }

    @Override
    public List<Product> addAllProducts(List<Product> products) {
        List<Product> list = new ArrayList();
        for (Product product : products) {
            list.add(this.addProduct(product));
        }
        return list;
    }

    @Override
    public void updateProduct(Product product) {

    }

    @Override
    public void deleteProduct(int ProductId) {

    }
}
