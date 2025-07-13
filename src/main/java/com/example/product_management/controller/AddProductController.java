package com.example.product_management.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class AddProductController {

    @FXML private TableView<Product> productTable;

    @FXML private TableColumn<Product, String> productIdColumn;
    @FXML private TableColumn<Product, String> productNameColumn;
    @FXML private TableColumn<Product, String> categoryColumn;
    @FXML private TableColumn<Product, String> priceColumn;
    @FXML private TableColumn<Product, String> quantityColumn;
    @FXML private TableColumn<Product, String> dateColumn;

    @FXML private TextField productId;
    @FXML private TextField productName;
    @FXML private TextField category;
    @FXML private TextField price;
    @FXML private TextField quantity;
    @FXML private TextField date;

    private ObservableList<Product> productList = FXCollections.observableArrayList();
    private Stage stage;

    public String getTitle() {
        return "Product Management : Add Product";
    }

    public void setStage(Stage stage) {
        this.stage = stage;
        stage.setTitle(getTitle());
    }

    @FXML
    public void initialize() {
        productIdColumn.setCellValueFactory(data -> data.getValue().productIdProperty());
        productNameColumn.setCellValueFactory(data -> data.getValue().productNameProperty());
        categoryColumn.setCellValueFactory(data -> data.getValue().productTypeProperty());
        priceColumn.setCellValueFactory(data -> data.getValue().priceProperty());
        quantityColumn.setCellValueFactory(data -> data.getValue().stockProperty());
        dateColumn.setCellValueFactory(data -> data.getValue().dateProperty());

        refreshTableFromDB();
    }

    @FXML
    private void onClickAdd() {
        if (productId.getText().isEmpty() || productName.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Product ID and Name are required.");
            return;
        }

        String id = productId.getText();
        String name = productName.getText();
        String categoryValue = category.getText();
        String priceValue = price.getText();
        String quantityValue = quantity.getText();
        String dateValue = date.getText();

        String url = "jdbc:sqlite:data.db";

        try (Connection conn = DriverManager.getConnection(url)) {
            String checkQuery = "SELECT quantity FROM products WHERE id = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setString(1, id);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                int existingQty = rs.getInt("quantity");
                int newQty = existingQty + Integer.parseInt(quantityValue);

                String updateQuery = "UPDATE products SET quantity = ? WHERE id = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                updateStmt.setInt(1, newQty);
                updateStmt.setString(2, id);
                updateStmt.executeUpdate();

                showAlert(Alert.AlertType.INFORMATION, "Stock Updated", "Existing product quantity has been updated.");
            } else {
                String insertQuery = "INSERT INTO products (id, name, category, price, quantity, date) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
                insertStmt.setString(1, id);
                insertStmt.setString(2, name);
                insertStmt.setString(3, categoryValue);
                insertStmt.setString(4, priceValue);
                insertStmt.setInt(5, Integer.parseInt(quantityValue));
                insertStmt.setString(6, dateValue);
                insertStmt.executeUpdate();

                showAlert(Alert.AlertType.INFORMATION, "Product Added", "New product has been added.");
            }

            clearFields();
            refreshTableFromDB();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to access or update the database.");
        }
    }

    private void refreshTableFromDB() {
        productList.clear();
        String url = "jdbc:sqlite:data.db";

        try (Connection conn = DriverManager.getConnection(url)) {
            String query = "SELECT * FROM products";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Product product = new Product(
                        String.valueOf(rs.getInt("id")),
                        rs.getString("name"),
                        rs.getString("category"),
                        String.valueOf(rs.getDouble("price")),
                        String.valueOf(rs.getInt("quantity")),
                        rs.getString("date")
                );
                productList.add(product);
            }

            productTable.setItems(productList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onClickBack() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/product_management/Homepage.fxml"));
        Parent root = loader.load();

        HomeController home = loader.getController();
        home.setStage(stage);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void clearFields() {
        productId.clear();
        productName.clear();
        category.clear();
        price.clear();
        quantity.clear();
        date.clear();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
