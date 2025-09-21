package com.example.product_management.controller;

import com.example.product_management.DpApply.Report.*;
import com.example.product_management.Utill.DatabaseConnection;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.YearMonth;
import java.util.List;
import java.util.Arrays;

public class MonthlyReportController {

    @FXML
    private ComboBox<String> monthComboBox;

    @FXML
    private ComboBox<String> yearComboBox;

    private Stage stage;

    private String selectedMonthName;
    private String selectedYear;

    public String getTitle() {
        return "Product Management: Monthly Report";
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
    public void initialize() {
        List<String> months = Arrays.asList(
                "January","February","March","April","May","June",
                "July","August","September","October","November","December"
        );
        monthComboBox.getItems().addAll(months);
        List<String> years = Arrays.asList("2023","2024","2025","2026","2027");
        yearComboBox.getItems().addAll(years);
    }

    public void setReportMonth(String month, String year) {
        System.out.println("Report Month set to: " + month + " " + year);
        this.selectedMonthName = month;
        this.selectedYear = year;
    }

    @FXML
    public void onGenerateMonthlyReport(ActionEvent event) {
        String month = monthComboBox.getValue();
        String year = yearComboBox.getValue();

        if(month == null || year == null) {
            showAlert(Alert.AlertType.WARNING, "Warning", "Please select both month and year!");
            return;
        }

        System.out.println("Generating report for: " + month + " " + year);

        try {
            ObservableList<StockEntry> products = ProductDAO.getAllProducts();

            int monthIndex = monthComboBox.getItems().indexOf(month) + 1;
            int yearValue = Integer.parseInt(year);
            YearMonth selectedMonth = YearMonth.of(yearValue, monthIndex);
            ObservableList<ProductOrderSummary> orders = OrderHistoryDAO.getOrderHistoryByMonth(selectedMonth);

            if(orders.isEmpty()) {
                System.out.println("No orders found for " + month + " " + year);
            }

            String fileName = "MonthlyReport_" + month + "-" + year;

            Report monthlyCSV = new MonthlyReport(orders, products, new CSVFormatter());
            monthlyCSV.generate(fileName);

            Report monthlyPDF = new MonthlyReport(orders, products, new PDFFormatter());
            monthlyPDF.generate(fileName);

            System.out.println("Reports generated successfully for " + month + " " + year);

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to generate reports: " + e.getMessage());
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/product_management/ReportActionPage.fxml"));
            Parent root = loader.load();

            ReportActionController controller = loader.getController();
            controller.setReportMonth(month, year);
            controller.setStage(stage);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Product Management: Report Action");
            stage.show();

        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load Report Action page: " + e.getMessage());
        }
    }

    @FXML
    public void onBackClick(ActionEvent event) {
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
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load Report Options page: " + e.getMessage());
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
