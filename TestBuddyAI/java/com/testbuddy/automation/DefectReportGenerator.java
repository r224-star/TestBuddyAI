package com.testbuddy.automation;

public class DefectReportGenerator {

    public DefectReportGenerator() {
    }

    public void generateReport(Object... data) {
        System.out.println("=================================");
        System.out.println("      DEFECT REPORT");
        System.out.println("=================================");

        if (data == null || data.length == 0) {
            System.out.println("No defect data available.");
        } else {
            for (Object item : data) {
                System.out.println(item);
            }
        }

        System.out.println("=================================");
    }
}