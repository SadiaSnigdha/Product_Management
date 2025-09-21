package com.example.product_management.DpApply;

import com.example.product_management.controller.StockEntry;

public interface Subject {
    void attach(Observer observer);
    void notifyObservers(StockEntry product, String message);
}
