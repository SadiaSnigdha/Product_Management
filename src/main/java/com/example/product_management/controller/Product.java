package com.example.product_management.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Product {
    private final StringProperty productId;
    private final StringProperty productName;
    private final StringProperty productType;
    private final StringProperty price;
    private final StringProperty stock;
    private final StringProperty date;

    public Product(String productId, String productName, String productType, String price, String stock, String date) {
        this.productId = new SimpleStringProperty(productId);
        this.productName = new SimpleStringProperty(productName);
        this.productType = new SimpleStringProperty(productType);
        this.price = new SimpleStringProperty(price);
        this.stock = new SimpleStringProperty(stock);
        this.date = new SimpleStringProperty(date);
    }

    public StringProperty productIdProperty() {
        return productId;
    }
    public StringProperty productNameProperty() {
        return productName;
    }
    public StringProperty productTypeProperty() {
        return productType;
    }
    public StringProperty priceProperty() {
        return price;
    }
    public StringProperty stockProperty() {
        return stock;
    }
    public StringProperty dateProperty() {
        return date;
    }

    public String getProductId() {
        return productId.get();
    }
    public String getProductName() {
        return productName.get();
    }
    public String getProductType() {
        return productType.get();
    }
    public String getPrice() {
        return price.get();
    }
    public String getStock() {
        return stock.get();
    }
    public String getDate() {
        return date.get();
    }

    public void setStock(String stock) {
        this.stock.set(stock);
    }
    public void setProductName(String name) {
        this.productName.set(name);
    }
    public void setProductType(String type) {
        this.productType.set(type);
    }
    public void setPrice(String price) {
        this.price.set(price);
    }
    public void setDate(String date) {
        this.date.set(date);
    }
}
