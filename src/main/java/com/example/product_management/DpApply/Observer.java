package com.example.product_management.DpApply;

import com.example.product_management.controller.Product;

public interface Observer {
    void update(Product product, String message);
}
