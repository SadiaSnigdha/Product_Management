package com.example.product_management.controller;

import com.example.product_management.ConnectDB;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ProductListController {

    @FXML
    private TableView<Product> productTable;

    @FXML
    private TableColumn<Product, String> idColumn;

    @FXML
    private TableColumn<Product, String> nameColumn;

    @FXML
    private TableColumn<Product, String> categoryColumn;

    @FXML
    private TableColumn<Product, String> priceColumn;

    @FXML
    private TableColumn<Product, String> quantityColumn;

    @FXML
    private TableColumn<Product, String> date;

    @FXML
    private Button back;

    private Stage stage;

    public String getTitle() {
        return "Product Management : Product List";
    }

    public void setStage(Stage stage) {
        this.stage = stage;
        stage.setTitle(getTitle());
    }

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("productId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("productType"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));

        ConnectDB db = new ConnectDB();
        ObservableList<Product> productList = db.initDB();

        productTable.setItems(productList);
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
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
