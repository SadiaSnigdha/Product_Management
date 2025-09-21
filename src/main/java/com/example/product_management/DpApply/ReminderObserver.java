package com.example.product_management.DpApply;

import com.example.product_management.controller.StockEntry;
import javafx.scene.control.Alert;

public class ReminderObserver implements Observer {
    @Override
    public void update(StockEntry product, String message) {
        showAlert(Alert.AlertType.WARNING, "Product Reminder", message);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
