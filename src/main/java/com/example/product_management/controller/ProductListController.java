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

public class ProductListController {

    @FXML private TableView<Product> productTable;
    @FXML private TableColumn<Product, String> idColumn;
    @FXML private TableColumn<Product, String> nameColumn;
    @FXML private TableColumn<Product, String> categoryColumn;
    @FXML private TableColumn<Product, String> priceColumn;
    @FXML private TableColumn<Product, String> quantityColumn;
    @FXML private TableColumn<Product, String> expireDateColumn;

    private ObservableList<Product> products = FXCollections.observableArrayList();
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
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("productType"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
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
                Product product = new Product(
                        String.valueOf(rs.getInt("id")),
                        rs.getString("name"),
                        rs.getString("category"),
                        String.valueOf(rs.getDouble("price")),
                        String.valueOf(rs.getInt("quantity")),
                        rs.getString("date"),
                        rs.getString("expiry_date")
                );

                // Safe parse expiry date
                LocalDate expiry = safeParseDate(product.getExpiryDate());
                if (expiry != null && !expiry.isAfter(LocalDate.now())) {
                    reminderSubject.notifyObservers(product,
                            "The product '" + product.getProductName() + "' has expired on " + product.getExpiryDate() + ". Consider deleting it.");
                }


                // Check for zero quantity
                int qty = safeParseInt(product.getStock());
                if (qty <= 1) {
                    reminderSubject.notifyObservers(product,
                            "The product '" + product.getProductName() + "' is low on stock (quantity: " + qty + "). Consider restocking.");
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

        String[] patterns = {"d-M-uuuu", "dd-MM-uuuu", "d/M/uuuu", "dd/MM/uuuu"};
        for (String pattern : patterns) {
            try {
                return LocalDate.parse(rawDate.trim(), DateTimeFormatter.ofPattern(pattern));
            } catch (DateTimeParseException ignored) {}
        }

        System.err.println("Unrecognized date format: " + rawDate);
        return null;
    }

    private int safeParseInt(String s) {
        try { return Integer.parseInt(s.trim()); }
        catch (Exception e) { return 0; }
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
