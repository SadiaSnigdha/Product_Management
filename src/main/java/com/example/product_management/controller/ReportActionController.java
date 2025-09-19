package com.example.product_management.controller;

import com.example.product_management.DpApply.Report.*;
import com.example.product_management.Utill.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class ReportActionController {

    private Stage stage;
    public String getTitle() {
        return "Product Management: Report Action";
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

    private LocalDate reportDate;
    private ReportContext context = new ReportContext();

    public void setReportDate(LocalDate date) {
        this.reportDate = date;
        System.out.println("📅 Report Date set to: " + date);
    }


    public void onDownloadPDF(ActionEvent event) {
        String fileName = "DailyReport_" + reportDate;
        context.setStrategy(new PDFReportAction());
        context.executeStrategy(fileName);
    }

    public void onDownloadCSV(ActionEvent event) {
        String fileName = "DailyReport_" + reportDate;
        context.setStrategy(new CSVReportAction());
        context.executeStrategy(fileName);
    }

    public void onShareReport(ActionEvent event) {
        String fileName = "DailyReport_" + reportDate;
        context.setStrategy(new ShareReportAction());
        context.executeStrategy(fileName);
    }

    public void onBackHome(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/product_management/ReportOptions.fxml"));
            Parent root = loader.load();

            ReportOptionController report = loader.getController();
            report.setStage(stage);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Product Management: Report Option");
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