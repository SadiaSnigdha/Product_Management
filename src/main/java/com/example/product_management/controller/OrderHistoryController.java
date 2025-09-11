package com.example.product_management.controller;

import com.example.product_management.Utill.DatabaseConnection;
import com.example.product_management.controller.OrderHistoryDAO;
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
import java.sql.SQLException;
import java.sql.Statement;

public class OrderHistoryController {

    private Stage stage;

    public String getTitle() {
        return "Product Management: Order History";
    }

    public void setStage(Stage stage) {
        this.stage = stage;
        if (this.stage != null) {
            this.stage.setTitle(getTitle());
            this.stage.setOnCloseRequest(event -> {
                try {
                    DatabaseConnection.getInstance().closeConnection();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @FXML
    private TableView<ProductOrderSummary> productOrderTable;

    @FXML
    private TableColumn<ProductOrderSummary, String> customerNameColumn;

    @FXML
    private TableColumn<ProductOrderSummary, String> phoneNumberColumn;

    @FXML
    private TableColumn<ProductOrderSummary, String> orderDateColumn;

    @FXML
    private TableColumn<ProductOrderSummary, Integer> productIdColumn;

    @FXML
    private TableColumn<ProductOrderSummary, String> productNameColumn;

    @FXML
    private TableColumn<ProductOrderSummary, Integer> quantityColumn;

//    private Connection getConnection() throws SQLException {
//        Connection conn = DatabaseConnection.getInstance().getConnection();
//        if (conn.isClosed()) {
//            throw new SQLException("Database connection is closed");
//        }
//        return conn;
//    }
//
//    private ObservableList<ProductOrderSummary> getOrderHistory() throws SQLException {
//        ObservableList<ProductOrderSummary> list = FXCollections.observableArrayList();
//        String sql = """
//            SELECT
//                c.name AS customer_name,
//                c.phone AS phone_number,
//                o.order_date AS order_date,
//                p.id AS product_id,
//                p.name AS product_name,
//                oi.quantity AS quantity
//            FROM order_items oi
//            JOIN orders o ON oi.order_id = o.id
//            JOIN customers c ON o.customer_id = c.id
//            JOIN products p ON oi.product_id = p.id
//            ORDER BY o.order_date DESC;
//        """;
//
//        try (Connection conn = getConnection();
//             Statement stmt = conn.createStatement();
//             ResultSet rs = stmt.executeQuery(sql)) {
//            while (rs.next()) {
//                list.add(new ProductOrderSummary(
//                        rs.getString("customer_name"),
//                        rs.getString("phone_number"),
//                        rs.getString("order_date"),
//                        rs.getInt("product_id"),
//                        rs.getString("product_name"),
//                        rs.getInt("quantity")
//                ));
//            }
//        }
//        return list;
//    }

    @FXML
    private void initialize() {
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        orderDateColumn.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("productId"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        try {
            productOrderTable.setItems(OrderHistoryDAO.getOrderHistory());
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load order history: " + e.getMessage());
        }
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