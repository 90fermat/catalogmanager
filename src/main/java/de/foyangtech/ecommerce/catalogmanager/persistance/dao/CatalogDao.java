package de.foyangtech.ecommerce.catalogmanager.persistance.dao;


import de.foyangtech.ecommerce.catalogmanager.persistance.model.Catalog;
import de.foyangtech.ecommerce.catalogmanager.persistance.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CatalogDao extends JpaRepository<Catalog, Long> {

    @Query(value = "SELECT * FROM Products p WHERE p.catalog_id = :id", nativeQuery = true )
    List<Product> findAllProductsById(@Param("id") Integer id);

    @Query(value = "SELECT title FROM Catalogs ", nativeQuery = true )
    List<String> findAllTile();
}
