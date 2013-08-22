package com.vadymlotar.demo.elasticsearchDemo.model;

import java.math.BigDecimal;

/**
 * This class is used for mapping products from the resource txt file to Java objects
 * User: vlotar
 */
public class Product {
    private String id;
    private String brand;
    private String name;
    private BigDecimal price;

    public Product(String id, String brand, String name, BigDecimal price) {
        this.id = id;
        this.brand = brand;
        this.name = name;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
