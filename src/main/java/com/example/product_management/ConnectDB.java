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
            connection = DriverManager.getConnection("jdbc:sqlite:data.db");
            statement = connection.createStatement();

            String sqlQuery = "SELECT * FROM products";
            ResultSet rs = statement.executeQuery(sqlQuery);

            while (rs.next()) {
                Product p = new Product(
                        String.valueOf(rs.getInt("id")),
                        rs.getString("name"),
                        rs.getString("category"),
                        String.valueOf(rs.getDouble("price")),
                        String.valueOf(rs.getInt("quantity")),
                        rs.getString("date")
                );
                products.add(p);
            }

            rs.close();
            statement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }
}
