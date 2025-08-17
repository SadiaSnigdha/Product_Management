package com.example.product_management.DpApply;

import com.example.product_management.controller.Product;

public interface Subject {
    void attach(Observer observer);
    void notifyObservers(Product product, String message);
}
