package com.testbuddy.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {

    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null) {
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter("reports/ExtentReport.html");
            sparkReporter.config().setDocumentTitle("TestBuddyAI Report");
            sparkReporter.config().setReportName("Automation Test Results");

            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
            extent.setSystemInfo("Project", "TestBuddyAI");
            extent.setSystemInfo("Tester", "Rajesh Kumar");
        }
        return extent;
    }
}