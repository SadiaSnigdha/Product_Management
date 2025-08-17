//package com.example.product_management.controller;
//
//import javafx.fxml.FXML;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Button;
//import javafx.scene.control.PasswordField;
//import javafx.scene.control.TextField;
//import javafx.stage.Stage;
//import org.mindrot.jbcrypt.BCrypt;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//
//public class ForgetPasswordController {
//
//    @FXML
//    private TextField emailField;
//
//    @FXML
//    private PasswordField newPasswordField;
//
//    @FXML
//    private PasswordField confirmPasswordField;
//
//    @FXML
//    private Button resetButton;  // Button কে অবশ্যই @FXML দিয়ে ডিক্লেয়ার করতে হবে
//
//    @FXML
//    private void onResetPassword() {
//        String email = emailField.getText().trim();
//        String newPass = newPasswordField.getText();
//        String confirmPass = confirmPasswordField.getText();
//
//        if (email.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()) {
//            showAlert(Alert.AlertType.ERROR, "Error", "Please fill in all fields.");
//            return;
//        }
//
//        if (!newPass.equals(confirmPass)) {
//            showAlert(Alert.AlertType.ERROR, "Error", "Passwords do not match.");
//            return;
//        }
//
//        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:data.db")) {
//            String sql = "UPDATE users SET password = ? WHERE email = ?";
//            PreparedStatement stmt = conn.prepareStatement(sql);
//
//            String hashed = BCrypt.hashpw(newPass, BCrypt.gensalt());
//            stmt.setString(1, hashed);
//            stmt.setString(2, email);
//
//            int updated = stmt.executeUpdate();
//            if (updated > 0) {
//                showAlert(Alert.AlertType.INFORMATION, "Success", "Password updated successfully!");
//
//                // Password আপডেট হবার পর উইন্ডো বন্ধ করা
//                Stage stage = (Stage) resetButton.getScene().getWindow();
//                stage.close();
//
//            } else {
//                showAlert(Alert.AlertType.ERROR, "Error", "User with this email not found.");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            showAlert(Alert.AlertType.ERROR, "Error", "Database error: " + e.getMessage());
//        }
//    }
//
//    private void showAlert(Alert.AlertType alertType, String title, String message) {
//        Alert alert = new Alert(alertType);
//        alert.setTitle(title);
//        alert.setHeaderText(null);
//        alert.setContentText(message);
//        alert.showAndWait();
//    }
//}

package com.example.product_management.controller;

import com.example.product_management.Utill.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ForgetPasswordController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Button resetButton;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
        if (this.stage != null) {
            this.stage.setTitle("Product Management: Reset Password");
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
    private void onResetPassword() {
        String email = emailField.getText().trim();
        String newPass = newPasswordField.getText();
        String confirmPass = confirmPasswordField.getText();

        if (email.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please fill in all fields.");
            return;
        }

        if (!newPass.equals(confirmPass)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Passwords do not match.");
            return;
        }

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE users SET password = ? WHERE email = ?")) {
            String hashed = BCrypt.hashpw(newPass, BCrypt.gensalt());
            stmt.setString(1, hashed);
            stmt.setString(2, email);

            int updated = stmt.executeUpdate();
            if (updated > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Password updated successfully!");
                Stage stage = (Stage) resetButton.getScene().getWindow();
                stage.close();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "User with this email not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Database error: " + e.getMessage());
        }
    }

    private Connection getConnection() throws SQLException {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        if (conn.isClosed()) {
            throw new SQLException("Database connection is closed");
        }
        return conn;
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}