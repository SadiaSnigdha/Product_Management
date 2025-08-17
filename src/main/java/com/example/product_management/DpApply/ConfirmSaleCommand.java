package com.example.product_management.DpApply;

import com.example.product_management.controller.CreateSalesController;

public class ConfirmSaleCommand implements Command {
    private final CreateSalesController controller;

    public ConfirmSaleCommand(CreateSalesController controller) {
        this.controller = controller;
    }

    @Override
    public void execute() {
        controller.onConfirmSale();
    }
}
