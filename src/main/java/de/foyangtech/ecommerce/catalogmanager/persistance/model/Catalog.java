package de.foyangtech.ecommerce.catalogmanager.persistance.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "catalog")
@Table(name = "catalogs")
public class Catalog {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    @OneToMany(mappedBy = "catalog",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Product> products = new ArrayList<>();

    public Catalog() {}

    public  Catalog(String title) {
        this.title = title;
    }

    public void addComment(Product product) {
        products.add(product);
        product.setCatalog(this);
    }

    public void removeComment(Product product) {
        products.remove(product);
        product.setCatalog(null);
    }
}
