package com.example.product_management.controller;

import com.example.product_management.Utill.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class ReportOptionController {
    private Stage stage;
    public String getTitle() {
        return "Product Management: Report Option";
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

    public void onDailyReportClick(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/product_management/DailyReportPage.fxml"));
            Parent root = loader.load();

            DailyReportController home = loader.getController();
            home.setStage(stage);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Product Management: Daily Report");
            stage.show();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load  daily report page: " + e.getMessage());
        }
    }

    public void onMonthlyReportClick(ActionEvent event) throws IOException {
        System.out.println("Monthly Report Clicked (To be implemented)");
    }

    public void onBackClick(ActionEvent event) throws IOException {
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

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
