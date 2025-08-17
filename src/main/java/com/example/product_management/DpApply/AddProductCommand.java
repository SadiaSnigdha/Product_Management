package com.example.product_management.DpApply;

import com.example.product_management.controller.CreateSalesController;
import com.example.product_management.controller.CartItem;

public class AddProductCommand implements Command {
    private final CartItem item;
    private final CreateSalesController controller;

    public AddProductCommand(CartItem item, CreateSalesController controller) {
        this.item = item;
        this.controller = controller;
    }

    @Override
    public void execute() {
        controller.addProductToCart(item);
    }
}
