package com.example.product_management.controller;

import com.example.product_management.controller.DBUtil;
import javafx.application.Platform;
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
import java.util.List;
import java.util.stream.Collectors;

public class CreateSalesController {

    @FXML private ComboBox<String> productComboBox;
    @FXML private TextField quantityField;
    @FXML private TextField phoneField;
    @FXML private Label customerName, customerEmail, totalLabel;
    @FXML private Button searchButton, addCustomerButton, sellButton;
    @FXML private TableView<CartItem> cartTable;
    @FXML private TableColumn<CartItem, String> productNameCol;
    @FXML private TableColumn<CartItem, Integer> quantityCol;
    @FXML private TableColumn<CartItem, Double> priceCol;
    @FXML private TableColumn<CartItem, Double> subtotalCol;

    private ObservableList<String> productNames = FXCollections.observableArrayList();
    private ObservableList<CartItem> cartItems = FXCollections.observableArrayList();
    private Connection connection;
    private Stage stage;

    public String getTitle() {
        return "Product Management : Sale Manage";
    }

    public void setStage(Stage stage) {
        this.stage = stage;
        stage.setTitle(getTitle());
    }

    public void initialize() {
        try {
            connection = DBUtil.getConnection();
            loadProducts();
            setupAutoComplete();
            setupCartTable();
        } catch (SQLException e) {
            showAlert("Error", "Database connection failed.");
        }
    }

    private void loadProducts() {
        productNames.clear();
        try {
            String query = "SELECT name FROM products";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                productNames.add(rs.getString("name"));
            }
            productComboBox.setItems(productNames);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setupAutoComplete() {
        productComboBox.setEditable(true);
        final Object lock = new Object();
        final boolean[] userInput = {false};

        productComboBox.getEditor().textProperty().addListener((obs, oldText, newText) -> {
            synchronized (lock) {
                if (!userInput[0]) {
                    userInput[0] = true;

                    List<String> filtered = productNames.stream()
                            .filter(item -> item.toLowerCase().contains(newText.toLowerCase()))
                            .collect(Collectors.toList());

                    productComboBox.setItems(FXCollections.observableArrayList(filtered));
                    if (!productComboBox.isShowing()) productComboBox.show();

                    productComboBox.getEditor().setText(newText);
                    productComboBox.getEditor().positionCaret(newText.length());

                    userInput[0] = false;
                }
            }
        });

        productComboBox.setOnAction(event -> {
            String selected = productComboBox.getSelectionModel().getSelectedItem();
            if (selected != null) {
                Platform.runLater(() -> {
                    productComboBox.getEditor().setText(selected);
                    productComboBox.getEditor().positionCaret(selected.length());
                });
            }
        });
    }

    private void setupCartTable() {
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        subtotalCol.setCellValueFactory(new PropertyValueFactory<>("subtotal"));

        cartTable.setItems(cartItems);
    }

    @FXML
    private void onSearchCustomer() {
        String phone = phoneField.getText().trim();
        if (phone.isEmpty()) {
            showAlert("Error", "Phone number is required.");
            return;
        }

        try {
            String query = "SELECT name, email FROM customers WHERE phone = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, phone);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                customerName.setText(rs.getString("name"));
                customerEmail.setText(rs.getString("email"));
                addCustomerButton.setVisible(false);
            } else {
                customerName.setText("-");
                customerEmail.setText("-");
                addCustomerButton.setVisible(true);
                showAlert("Not Found", "Customer not found. Please add.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onAddCustomer() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/product_management/AddCustomer.fxml"));
            Parent root = loader.load();

            AddCustomerController controller = loader.getController();
            Stage stage = new Stage();
            controller.setStage(stage);

            controller.setOnCustomerAdded(() -> {
                phoneField.setText(phoneField.getText().trim());
                onSearchCustomer();
            });

            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load Add Customer window.");
        }
    }

    @FXML
    private void onConfirmSale() {
        double total = cartItems.stream().mapToDouble(CartItem::getSubtotal).sum();
        totalLabel.setText(String.valueOf(total));
        showAlert("Success", "Sale confirmed! Total: " + total);
        cartItems.clear();
    }

    @FXML
    private void onAddToCart() {
        String product = productComboBox.getValue();
        String qtyStr = quantityField.getText();

        if (product == null || product.isEmpty() || qtyStr.isEmpty()) {
            showAlert("Input Error", "Please select product and quantity.");
            return;
        }

        try {
            int quantity = Integer.parseInt(qtyStr);
            double price = getPriceOfProduct(product);

            cartItems.add(new CartItem(product, quantity, price));
            updateTotal();
            quantityField.clear();
            productComboBox.getSelectionModel().clearSelection();
            productComboBox.getEditor().clear();
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Quantity must be a number.");
        }
    }

    private void updateTotal() {
        double total = cartItems.stream().mapToDouble(CartItem::getSubtotal).sum();
        totalLabel.setText(String.valueOf(total));
    }

    private double getPriceOfProduct(String productName) {
        try {
            String query = "SELECT price FROM products WHERE name = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, productName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble("price");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
