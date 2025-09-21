package com.example.product_management.DpApply.Report;

import com.example.product_management.controller.StockEntry;
import com.example.product_management.controller.ProductOrderSummary;
import javafx.collections.ObservableList;

public class MonthlyReport extends Report {

    private ObservableList<ProductOrderSummary> orderList;
    private ObservableList<StockEntry> productList;

    public MonthlyReport(ObservableList<ProductOrderSummary> orderList,
                         ObservableList<StockEntry> productList,
                         ReportFormatter formatter) {
        super(formatter);
        this.orderList = orderList;
        this.productList = productList;
    }

    @Override
    public void generate(String fileName) {
        double totalAmount = 0;
        double totalProfit = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("Customer,Phone,Date,Product,Quantity,Price,Total,Profit\n");

        for (ProductOrderSummary order : orderList) {
            StockEntry matchedProduct = null;
            for (StockEntry p : productList) {
                if (p.getProductId() == order.getProductId()) {
                    matchedProduct = p;
                    break;
                }
            }

            if (matchedProduct == null) continue;

            double price = matchedProduct.getSellPrice();
            double buyPrice = matchedProduct.getBuyPrice();
            double total = price * order.getQuantity();
            double profit = (price - buyPrice) * order.getQuantity();

            totalAmount += total;
            totalProfit += profit;

            sb.append(order.getCustomerName()).append(",")
                    .append(order.getPhoneNumber()).append(",")
                    .append(order.getOrderDate()).append(",")
                    .append(order.getProductName()).append(",")
                    .append(order.getQuantity()).append(",")
                    .append(price).append(",")
                    .append(total).append(",")
                    .append(profit).append("\n");
        }

        sb.append("\nTotal Amount: ").append(totalAmount)
                .append(", Total Profit: ").append(totalProfit);

        formatter.generateFile(sb.toString(), fileName);
    }
}
