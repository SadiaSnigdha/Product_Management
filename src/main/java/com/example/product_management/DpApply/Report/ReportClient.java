//package com.example.product_management.DpApply.Report;
//
//import com.example.product_management.controller.*;
//import javafx.collections.ObservableList;
//
//public class ReportClient {
//    public static void main(String[] args) {
//        try {
//            // Fetch from DB
//            ObservableList<Product> products = ProductDAO.getAllProducts();
//            ObservableList<ProductOrderSummary> orders = OrderHistoryDAO.getOrderHistory();
//
//            // Daily report in CSV
//            Report dailyCSV = new DailyReport(orders, products, new CSVFormatter());
//            dailyCSV.generate("DailyReport_2025_09_05");
//
//            // Daily report in PDF
//            Report dailyPDF = new DailyReport(orders, products, new PDFFormatter());
//            dailyPDF.generate("DailyReport_2025_09_05");
//
//            System.out.println("Reports generated successfully.");
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//            System.err.println("Failed to generate reports: " + e.getMessage());
//        }
//    }
//}
