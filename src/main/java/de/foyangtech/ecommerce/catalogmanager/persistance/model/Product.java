package de.foyangtech.ecommerce.catalogmanager.persistance.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
@Component
@Table(name ="products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Length(min = 3, max = 20)
    private String name;


    @NotNull
    @Length(min = 3, max = 20)
    @Column(unique = true)
    private String code;

    @ManyToMany(mappedBy = "products")
    @JsonBackReference
    private Set<Catalog> catalogs = new HashSet<>();

    @Min(1)
    private double sellingPrice;
    @Min(1)
    private double buyingPrice;

    @Length(min = 3, max = 20)
    private String supplierName;

    @Length(min = 10, max = 200)
    private String supplierUrl;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(unique = true, referencedColumnName = "id")
    private ProductImage image;

    @Basic(optional = false)
    @Column(name="timestamp", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    private String category;


    @Transient
    private MultipartFile multipartFile;



    public Product() {
       // setProductId(new ProductId());
    }

    public Product(@NotNull @Length(min = 3, max = 20) String name,
                   @NotNull @Length(min = 3, max = 20) String code,
                   @Min(1) double sellingPrice,
                   @Min(1) double buyingPrice,
                   @Length(min = 3, max = 20) String supplierName,
                   @Length(min = 10, max = 200) String supplierUrl,
                    String category, ProductImage image, Date timestamp)
    {
        this.name = name;
        this.code = code;
        this.sellingPrice = sellingPrice;
        this.buyingPrice = buyingPrice;
        this.supplierName = supplierName;
        this.supplierUrl = supplierUrl;
        this.category = category;
        this.image  = image;
        this.timestamp = timestamp;
        this.image.setProduct(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product )) return false;
        return id != null && id.equals(((Product) o).getId());
    }
    @Override
    public int hashCode() {
        if(code != null) {
            return 31 + code.hashCode();
        }
       return 31;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public double getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(double buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierUrl() {
        return supplierUrl;
    }

    public void setSupplierUrl(String supplierUrl) {
        this.supplierUrl = supplierUrl;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }

    public ProductImage getImage() {
        return image;
    }

    public void setImage(ProductImage image) {
        this.image = image;
    }

    public Set<Catalog> getCatalogs() {
        return catalogs;
    }


//
//    public ProductId getProductId() {
//        return productId;
//    }
//
//    public void setProductId(ProductId productId) {
//        this.productId = productId;
//    }


    @Override
    public String toString() {
        String toString = "Product { " +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", selling Price=" + sellingPrice +
                ", buying Price=" + buyingPrice +
                ", supplier Name='" + supplierName + '\'' +
                ", supplier Url='" + supplierUrl + '\'' +
                ", category='" + category + '\'' +
                ", timestamp=" + timestamp ;
        if (image != null) {
            return toString +  ", image Name=" + image.getFileName() +  '}';
        }
        return toString +  '}';
    }
}
