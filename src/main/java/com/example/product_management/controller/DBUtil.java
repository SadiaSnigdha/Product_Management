package com.example.product_management.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection("jdbc:sqlite:data.db");
            connection.createStatement().execute("PRAGMA busy_timeout = 3000");
        }
        return connection;
    }
}
