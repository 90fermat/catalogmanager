package com.ecommerce.catalogmanager.persistance.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;

public class Product {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Length(min = 3, max = 20)
    private String name;

    @Min(1)
    private int price;


    private int BuyingPrice;


    private String supplierName;

    private String supplierUrl;




    //default constructor
    public Product() {
    }

    public Product(int id, String name, int price, int BuyingPrice) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.BuyingPrice = BuyingPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getBuyingPrice() {
        return BuyingPrice;
    }

    public void setBuyingPrice(int buyingPrice) {
        this.BuyingPrice = buyingPrice;
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
    @Override
    public String toString() {
        return "Product{"+
                "id=" + id +
                ", name='" + name +'\'' +
                ", price=" + price +
                ", BuyingPrice=" + BuyingPrice +'}';
    }
}
