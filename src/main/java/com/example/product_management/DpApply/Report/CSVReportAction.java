package com.example.product_management.DpApply.Report;

public class CSVReportAction implements ReportActionStrategy {
    @Override
    public void execute(String fileName) {
        System.out.println("Download CSV report: " + fileName);
    }
}
