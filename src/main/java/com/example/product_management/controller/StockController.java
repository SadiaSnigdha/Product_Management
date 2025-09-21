package com.example.product_management.controller;

import com.example.product_management.DpApply.ReminderObserver;
import com.example.product_management.DpApply.ReminderSubject;
import com.example.product_management.Utill.DatabaseConnection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class StockController {

    @FXML private TableView<StockEntry> productTable;
    @FXML private TableColumn<StockEntry, Integer> idColumn;
    @FXML private TableColumn<StockEntry, String> nameColumn;
    @FXML private TableColumn<StockEntry, String> categoryColumn;
    @FXML private TableColumn<StockEntry, Double> buyPriceColumn;
    @FXML private TableColumn<StockEntry, Double> sellPriceColumn;
    @FXML private TableColumn<StockEntry, Integer> quantityColumn;
    @FXML private TableColumn<StockEntry, String> dateColumn;
    @FXML private TableColumn<StockEntry, String> expireDateColumn;

    private final ObservableList<StockEntry> products = FXCollections.observableArrayList();
    private final ReminderSubject reminderSubject = new ReminderSubject();
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        reminderSubject.attach(new ReminderObserver());

        idColumn.setCellValueFactory(new PropertyValueFactory<>("productId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        buyPriceColumn.setCellValueFactory(new PropertyValueFactory<>("buyPrice"));
        sellPriceColumn.setCellValueFactory(new PropertyValueFactory<>("sellPrice"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        expireDateColumn.setCellValueFactory(new PropertyValueFactory<>("expiryDate"));

        loadProducts();
        productTable.setItems(products);
    }

    private void loadProducts() {
        products.clear();

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM products")) {

            while (rs.next()) {
                StockEntry product = new StockEntry(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getDouble("buy_price"),
                        rs.getDouble("sell_price"),
                        rs.getInt("quantity"),
                        rs.getString("date"),
                        rs.getString("expiry_date")
                );

                // expiry check
                LocalDate expiry = safeParseDate(product.getExpiryDate());
                if (expiry != null && expiry.isBefore(LocalDate.now())) {
                    reminderSubject.notifyObservers(product,
                            "⚠ The product '" + product.getProductName() +
                                    "' has expired on " + expiry + ". Consider deleting it.");
                }

                // stock check
                if (product.getQuantity() <= 1) {
                    reminderSubject.notifyObservers(product,
                            "⚠ The product '" + product.getProductName() +
                                    "' is low on stock (quantity: " + product.getQuantity() + "). Consider restocking.");
                }

                products.add(product);
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to load products: " + e.getMessage());
        }
    }

    private LocalDate safeParseDate(String rawDate) {
        if (rawDate == null || rawDate.isBlank()) return null;

        rawDate = rawDate.trim();

        String[] patterns = {
                "d-M-uuuu", "dd-MM-uuuu", "d/M/uuuu", "dd/MM/uuuu", "uuuu-MM-dd"
        };
        for (String pattern : patterns) {
            try {
                return LocalDate.parse(rawDate, DateTimeFormatter.ofPattern(pattern));
            } catch (DateTimeParseException ignored) {}
        }

        String[] yyPatterns = {"d-M-yy", "dd-MM-yy", "d/M/yy", "dd/MM/yy", "yy-MM-dd"};
        for (String pattern : yyPatterns) {
            try {
                LocalDate parsed = LocalDate.parse(rawDate, DateTimeFormatter.ofPattern(pattern));
                if (parsed.getYear() < 100) {
                    return parsed.withYear(2000 + parsed.getYear());
                }
                return parsed;
            } catch (DateTimeParseException ignored) {}
        }

        System.err.println("Unrecognized date format: " + rawDate);
        return null;
    }

    @FXML
    public void onClickBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/product_management/Homepage.fxml"));
            Parent root = loader.load();

            HomeController home = loader.getController();
            home.setStage(stage);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Product Management: Home");
            stage.show();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load Home page: " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
