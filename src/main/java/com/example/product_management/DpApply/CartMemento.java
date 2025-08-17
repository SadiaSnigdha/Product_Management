package com.example.product_management.DpApply;


import java.util.List;
import com.example.product_management.controller.CartItem;

public class CartMemento {
    private final List<CartItem> cartState;

    public CartMemento(List<CartItem> cartState) {
        // deep copy to prevent modification of original cart
        this.cartState = cartState.stream()
                .map(item -> new CartItem(item.getProductName(), item.getQuantity(), item.getPrice()))
                .toList();
    }

    public List<CartItem> getSavedState() {
        return cartState;
    }
}