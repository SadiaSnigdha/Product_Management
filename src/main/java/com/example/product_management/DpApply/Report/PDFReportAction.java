package com.example.product_management.DpApply.Report;

public class PDFReportAction implements ReportActionStrategy {
    @Override
    public void execute(String fileName) {
        System.out.println("Download PDF report: " + fileName);
    }
}