package com.ecommerce.catalogmanager.persistance.dao;

import com.ecommerce.catalogmanager.persistance.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDao extends JpaRepository<Product, Long> {

    Product findById(int id);

    List<Product> findByPriceGreaterThan(int limitPrice);

    List<Product> findByNameLike(String search);
}
