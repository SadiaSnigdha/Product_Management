package com.example.product_management.controller;

public class Product {
    private String productId;
    private String productName;
    private String productType;
    private String price;
    private String stock;
    private String date;
    private String expiryDate;

    public Product(String productId, String productName, String productType, String price, String stock, String date, String expiryDate) {
        this.productId = productId;
        this.productName = productName;
        this.productType = productType;
        this.price = price;
        this.stock = stock;
        this.date = date;
        this.expiryDate = expiryDate;
    }

    public String getProductId() { return productId; }
    public String getProductName() { return productName; }
    public String getProductType() { return productType; }
    public String getPrice() { return price; }
    public String getStock() { return stock; }
    public String getDate() { return date; }
    public String getExpiryDate() { return expiryDate; }

    public void setProductId(String productId) { this.productId = productId; }
    public void setProductName(String productName) { this.productName = productName; }
    public void setProductType(String productType) { this.productType = productType; }
    public void setPrice(String price) { this.price = price; }
    public void setStock(String stock) { this.stock = stock; }
    public void setDate(String date) { this.date = date; }
    public void setExpiryDate(String expiryDate) { this.expiryDate = expiryDate; }
}
