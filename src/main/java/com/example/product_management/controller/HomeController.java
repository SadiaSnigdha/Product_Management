package com.example.product_management.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void onAddProductClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/product_management/AddProduct.fxml"));
            Parent root = loader.load();
            AddProductController addProductController = loader.getController();
            addProductController.setStage(stage);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e) {
            System.out.println("Error loading Add Product page.");
            e.printStackTrace();
        }
    }

    @FXML
    private void onProductListClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/product_management/ProductList.fxml"));
            Parent root = loader.load();
            ProductListController productListController = loader.getController();
            productListController.setStage(stage);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e) {
            System.out.println("Error loading Product List page.");
            e.printStackTrace();
        }
    }

    @FXML
    private void onCustomerListClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/product_management/CustomerList.fxml"));
            Parent root = loader.load();
            CustomerListController customerListController = loader.getController();
            customerListController.setStage(stage);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Customer List");
            stage.show();
        }
        catch (IOException e) {
            System.out.println("Error loading Customer List page.");
            e.printStackTrace();
        }
    }

    @FXML
    private void onOrderHistoryClick() {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/product_management/OrderHistory.fxml"));
//            Parent root = loader.load();
//            Scene scene = new Scene(root);
//            stage.setScene(scene);
//            stage.setTitle("Order History");
//            stage.show();
//        } catch (IOException e) {
//            System.out.println("Error loading Order History page.");
//            e.printStackTrace();
//        }
    }

    @FXML
    private void onCreateSalesClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/product_management/CreateSale.fxml"));
            Parent root = loader.load();
            CreateSalesController createSalesController = loader.getController();
            createSalesController.setStage(stage);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e) {
            System.out.println("Error loading Create Sales page.");
            e.printStackTrace();
        }
    }
}
