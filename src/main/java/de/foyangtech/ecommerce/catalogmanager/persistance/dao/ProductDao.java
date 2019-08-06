package de.foyangtech.ecommerce.catalogmanager.persistance.dao;

import de.foyangtech.ecommerce.catalogmanager.persistance.model.Product;
import org.hibernate.type.BlobType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.Date;
import java.util.List;
import static java.sql.Types.BLOB;

@Repository
public interface ProductDao extends JpaRepository<Product, Long> {


    Product findByName(String name);

    List<Product> findByBuyingPriceGreaterThan(int limitPrice);

    List<Product> findByNameLike(String search);

    List<Product> findProductsByTimestampIsGreaterThanEqual(Date date);

    @Query(value = "SELECT image_id FROM Products p WHERE p.id = :id", nativeQuery = true )
    String findPhotoById(@Param("id") Integer id);



}
