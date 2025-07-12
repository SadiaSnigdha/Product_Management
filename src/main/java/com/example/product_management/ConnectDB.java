package com.example.product_management;

import com.example.product_management.controller.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConnectDB {

    private static Statement statement;
    private static Connection connection;

    public ObservableList<Product> initDB() {
        ObservableList<Product> products = FXCollections.observableArrayList();

        try {
            // SQLite database connect
            connection = DriverManager.getConnection("jdbc:sqlite:data.db");
            statement = connection.createStatement();

            // Execute SQL query
            String sqlQuery = "SELECT * FROM products";
            ResultSet rs = statement.executeQuery(sqlQuery);

            // Clear existing list
            products.clear();

            // Iterate and fetch data
            while (rs.next()) {
                Product p = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getDouble("price"),
                        rs.getInt("quantity"),
                        rs.getString("date")  // Make sure 'date' column exists in your DB
                );
                products.add(p);
            }

            // Close connections
            rs.close();
            statement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }
}
