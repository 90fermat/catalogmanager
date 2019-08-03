package de.foyangtech.ecommerce.catalogmanager.persistance.dao;

import de.foyangtech.ecommerce.catalogmanager.persistance.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ProductDao extends JpaRepository<Product, Long> {


    Product findByName(String name);

    List<Product> findByBuyingPriceGreaterThan(int limitPrice);

    List<Product> findByNameLike(String search);

    List<Product> findProductsByTimestampIsGreaterThanEqual(Date date);
}
