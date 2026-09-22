package com.testbuddy.automation;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;

public class ExtentReportManager {

    private static ExtentReports extent;
    private static final String REPORT_DIR = "reports";
    private static final String REPORT_PATH = "reports/SelfHealingTestReport.html";

    public static synchronized ExtentReports getInstance() {
        if (extent == null) {
            File dir = new File(REPORT_DIR);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(REPORT_PATH);
            sparkReporter.config().setReportName("TestBuddyAI Autonomous Healing Execution");
            sparkReporter.config().setDocumentTitle("TestBuddyAI Test Report");
            sparkReporter.config().setTheme(Theme.DARK);

            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
            extent.setSystemInfo("Application", "TestBuddyAI Agent");
            extent.setSystemInfo("Environment", "QA Automation");
            extent.setSystemInfo("Engine", "Autonomous Self-Healing Selenium");
        }
        return extent;
    }

    public static ExtentTest createTest(String testName, String description) {
        return getInstance().createTest(testName, description);
    }

    public static void flush() {
        if (extent != null) {
            extent.flush();
        }
    }
}