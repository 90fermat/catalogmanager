package de.foyangtech.ecommerce.catalogmanager.persistance.model;

import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.util.Date;

@Entity
@Component
@Table(name ="products")
public class Product {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Length(min = 3, max = 20)
    private String name;

    @NotNull
    @Length(min = 3, max = 20)
    private String code;

    @Min(1)
    private double sellingPrice;
    @Min(1)
    private double buyingPrice;

    @Length(min = 3, max = 20)
    private String supplierName;

    @Length(min = 10, max = 200)
    private String supplierUrl;

    @NotNull
    private File image;

    @Basic(optional = false)
    @Column(name="timestamp", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    public Product() {
    }

    public Product(@NotNull @Length(min = 3, max = 20) String name,
                   @NotNull @Length(min = 3, max = 20) String code,
                   @Min(1) double sellingPrice,
                   @Min(1) double buyingPrice,
                   @Length(min = 3, max = 20) String supplierName,
                   @Length(min = 10, max = 200) String supplierUrl,
                   @NotNull File image)
    {
        this.name = name;
        this.code = code;
        this.sellingPrice = sellingPrice;
        this.buyingPrice = buyingPrice;
        this.supplierName = supplierName;
        this.supplierUrl = supplierUrl;
        this.image = image;
    }

    public int getId() {
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

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", sellingPrice=" + sellingPrice +
                ", buyingPrice=" + buyingPrice +
                ", supplierName='" + supplierName + '\'' +
                ", supplierUrl='" + supplierUrl + '\'' +
                ", image=" + image +
                ", timestamp=" + timestamp +
                '}';
    }
}
