package com.example.product_management.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class AddProductController {

    @FXML private TableView<Product> productTable;
    @FXML private TableColumn<Product, String> productIdColumn;
    @FXML private TableColumn<Product, String> productNameColumn;
    @FXML private TableColumn<Product, String> typeColumn;
    @FXML private TableColumn<Product, String> priceColumn;
    @FXML private TableColumn<Product, String> stockColumn;
    @FXML private TableColumn<Product, String> dateColumn;

    @FXML private TextField productId;
    @FXML private TextField productName;
    @FXML private TextField productType;
    @FXML private TextField price;
    @FXML private TextField stock;
    @FXML private TextField date;

    private ObservableList<Product> productList = FXCollections.observableArrayList();

    private Stage stage;
    public String getTitle() {
        return "Product Management : add product";
    }
    public void setStage(Stage stage) {
        this.stage = stage;
        stage.setTitle(getTitle());
    }

//    @FXML
//    public void initialize() {
//        // Set up table columns
//        productIdColumn.setCellValueFactory(data -> data.getValue().productIdProperty());
//        productNameColumn.setCellValueFactory(data -> data.getValue().productNameProperty());
//        typeColumn.setCellValueFactory(data -> data.getValue().productTypeProperty());
//        priceColumn.setCellValueFactory(data -> data.getValue().priceProperty());
//        stockColumn.setCellValueFactory(data -> data.getValue().stockProperty());
//        dateColumn.setCellValueFactory(data -> data.getValue().dateProperty());
//
//        productTable.setItems(productList);
//    }

   @FXML
    private void onClickAdd() {
//        if (productId.getText().isEmpty() || productName.getText().isEmpty()) {
//            showAlert(Alert.AlertType.ERROR, "Input Error", "Product ID and Name are required.");
//            return;
//        }
//
//        // Check if product exists, update stock if yes
//        for (Product p : productList) {
//            if (p.getProductId().equals(productId.getText())) {
//                int updatedStock = Integer.parseInt(p.getStock()) + Integer.parseInt(stock.getText());
//                p.setStock(String.valueOf(updatedStock));
//                productTable.refresh();
//                clearFields();
//                return;
//            }
//        }
//
//        // Add new product
//        Product newProduct = new Product(
//                productId.getText(),
//                productName.getText(),
//                productType.getText(),
//                price.getText(),
//                stock.getText(),
//                date.getText()
//        );
//        productList.add(newProduct);
//        clearFields();
    }

    @FXML
    private void onClickUpdate() {
//        for (Product p : productList) {
//            if (p.getProductId().equals(productId.getText())) {
//                p.setProductName(productName.getText());
//                p.setProductType(productType.getText());
//                p.setPrice(price.getText());
//                p.setStock(stock.getText());
//                p.setDate(date.getText());
//                productTable.refresh();
//                clearFields();
//                return;
//            }
//        }
//        showAlert(Alert.AlertType.WARNING, "Update Failed", "No product with this ID found.");
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
        productType.clear();
        price.clear();
        stock.clear();
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
