package de.foyangtech.ecommerce.catalogmanager.persistance.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.*;

@Entity(name = "catalog")
@Component
@Table(name = "catalogs")
public class Catalog {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "catalog_product",
            joinColumns = @JoinColumn(name = "catalog_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id")
    )
    @JsonManagedReference
    private Set<Product> products = new HashSet<>();

    public Catalog() {}

    public  Catalog(String title) {
        this.title = title;
    }

    public void addProduct(Product product) {
        products.add(product);
        product.getCatalogs().add(this);
    }

    public void removeProduct(Product product) {
        products.remove(product);
        product.getCatalogs().remove(this);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Product> getProducts() {
        return products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Catalog tag = (Catalog) o;
        return Objects.equals(title, tag.title);
    }

    @Override
    public int hashCode() {
        if(title != null)
            return Objects.hash(title);

        return 31;
    }

    @Override
    public String toString() {
       String partOne = "Catalog { " +
                            "id=" + id +
                             ", title='" + title + '\'' ;

       if (!products.isEmpty()) {
           String lastPart = "Products [ ";
           for(Product product : products) {
               lastPart = lastPart + '\n' + product.toString() + '\n';
           }
           lastPart = lastPart + "]";

           return partOne + lastPart;
       }
       return partOne + "}";

    }
}
