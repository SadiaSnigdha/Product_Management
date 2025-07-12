package com.example.product_management.controller;

import javafx.beans.property.*;

public class Product {
    private IntegerProperty id;
    private StringProperty name;
    private StringProperty category;
    private DoubleProperty price;
    private IntegerProperty quantity;
    private StringProperty date;

    public Product(int id, String name, String category, Double price, int quantity, String date) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.category = new SimpleStringProperty(category);
        this.price = new SimpleDoubleProperty(price);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.date = new SimpleStringProperty(date);
    }

    // Getters
    public int getId() {
        return id.get();
    }
    public String getName() {
        return name.get();
    }
    public String getCategory() {
        return category.get();
    }
    public double getPrice() {
        return price.get();
    }
    public int getQuantity() {
        return quantity.get();
    }
    public String getDate() {
        return date.get();
    }

    // Setters
    public void setId(int id) {
        this.id.set(id);
    }
    public void setName(String name) {
        this.name.set(name);
    }
    public void setCategory(String category) {
        this.category.set(category);
    }
    public void setPrice(double price) {
        this.price.set(price);
    }
    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }
    public void setDate(String date) {
        this.date.set(date);
    }

    // Property methods — JavaFX TableView uses these
    public IntegerProperty idProperty() {
        return id;
    }
    public StringProperty nameProperty() {
        return name;
    }
    public StringProperty categoryProperty() {
        return category;
    }
    public DoubleProperty priceProperty() {
        return price;
    }
    public IntegerProperty quantityProperty() {
        return quantity;
    }
    public StringProperty dateProperty() {
        return date;
    }
}
