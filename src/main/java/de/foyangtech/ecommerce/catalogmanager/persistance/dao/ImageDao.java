package de.foyangtech.ecommerce.catalogmanager.persistance.dao;

import de.foyangtech.ecommerce.catalogmanager.persistance.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ImageDao extends JpaRepository<ProductImage, String> {

    @Query(value = "SELECT photo FROM Images i WHERE i.id = :id", nativeQuery = true)
    byte[] findDataById(@Param("id") String id);

}
