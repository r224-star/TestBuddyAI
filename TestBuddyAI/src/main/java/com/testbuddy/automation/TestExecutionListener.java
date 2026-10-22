package com.testbuddy.automation;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.testbuddy.utils.ExtentManager;
import com.testbuddy.utils.FailureInfo;
import com.testbuddy.utils.ScreenshotUtil;
import com.testbuddy.utils.TestResult;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class TestExecutionListener implements ITestListener {

    // Stores failures of the CURRENT test execution
    private static final List<FailureInfo> failures = new ArrayList<>();

    // Execution counters
    private static int passedCount = 0;
    private static int failedCount = 0;
    private static int skippedCount = 0;

    private final TestResultDAO resultDAO = new TestResultDAO();

    private ExtentReports extent;
    private ExtentTest extentTest;

    @Override
    public void onStart(ITestContext context) {

        // Reset previous execution data
        failures.clear();

        passedCount = 0;
        failedCount = 0;
        skippedCount = 0;

        extent = ExtentManager.getInstance();

        System.out.println("========== TEST EXECUTION STARTED ==========");
    }

    // =========================
    // FAILURE DETAILS
    // =========================

    public static List<FailureInfo> getFailures() {
        return failures;
    }

    // =========================
    // EXECUTION COUNTS
    // =========================

    public static int getPassedCount() {
        return passedCount;
    }

    public static int getFailedCount() {
        return failedCount;
    }

    public static int getSkippedCount() {
        return skippedCount;
    }

    public static int getTotalCount() {
        return passedCount + failedCount + skippedCount;
    }

    // =========================
    // TEST START
    // =========================

    @Override
    public void onTestStart(ITestResult result) {

        extentTest = extent.createTest(
                result.getMethod().getMethodName()
        );

        System.out.println(
                "STARTED: " + result.getMethod().getMethodName()
        );
    }

    // =========================
    // TEST FAILURE
    // =========================

    @Override
    public void onTestFailure(ITestResult result) {

        failedCount++;

        String testName = result.getMethod().getMethodName();

        String errorMessage =
                result.getThrowable() != null
                        ? result.getThrowable().getMessage()
                        : "Unknown error";

        // Full stack trace
        StringWriter sw = new StringWriter();

        if (result.getThrowable() != null) {
            result.getThrowable().printStackTrace(
                    new PrintWriter(sw)
            );
        }

        // Store failure information
        failures.add(
                new FailureInfo(
                        testName,
                        errorMessage,
                        sw.toString()
                )
        );

        System.out.println(
                "FAILED: " + testName + " -> " + errorMessage
        );

        // Screenshot
        String screenshotPath = null;

        try {

            screenshotPath =
                    ScreenshotUtil.captureScreenshot(
                            DriverManager.getDriver(),
                            testName
                    );

        } catch (Exception e) {

            System.out.println(
                    "Could not capture screenshot: "
                            + e.getMessage()
            );
        }

        // Extent report
        if (extentTest != null) {

            extentTest.log(
                    Status.FAIL,
                    "Test Failed: " + errorMessage
            );

            if (screenshotPath != null) {

                try {

                    extentTest.addScreenCaptureFromPath(
                            screenshotPath
                    );

                } catch (Exception e) {

                    System.out.println(
                            "Could not attach screenshot to report: "
                                    + e.getMessage()
                    );
                }
            }
        }

        // Save result to DB
        saveToDb(
                testName,
                "FAILED",
                errorMessage
        );
    }

    // =========================
    // TEST SUCCESS
    // =========================

    @Override
    public void onTestSuccess(ITestResult result) {

        passedCount++;

        String testName =
                result.getMethod().getMethodName();

        System.out.println(
                "PASSED: " + testName
        );

        if (extentTest != null) {

            extentTest.log(
                    Status.PASS,
                    "Test Passed"
            );
        }

        // Save result to DB
        saveToDb(
                testName,
                "PASSED",
                null
        );
    }

    // =========================
    // TEST SKIPPED
    // =========================

    @Override
    public void onTestSkipped(ITestResult result) {

        skippedCount++;

        String testName =
                result.getMethod().getMethodName();

        System.out.println(
                "SKIPPED: " + testName
        );

        if (extentTest != null) {

            extentTest.log(
                    Status.SKIP,
                    "Test Skipped"
            );
        }

        // Save result to DB
        saveToDb(
                testName,
                "SKIPPED",
                null
        );
    }

    // =========================
    // DATABASE
    // =========================

    private void saveToDb(
            String testName,
            String status,
            String errorMessage) {

        try {

            resultDAO.saveResult(
                    new TestResult(
                            testName,
                            status,
                            errorMessage
                    )
            );

        } catch (Exception e) {

            System.out.println(
                    "Could not save result to DB: "
                            + e.getMessage()
            );
        }
    }

    // =========================
    // TEST EXECUTION FINISHED
    // =========================

    @Override
    public void onFinish(ITestContext context) {

        if (extent != null) {
            extent.flush();
        }

        System.out.println(
                "========== TEST EXECUTION FINISHED =========="
        );

        System.out.println(
                "Total Tests  : " + getTotalCount()
        );

        System.out.println(
                "Passed       : " + getPassedCount()
        );

        System.out.println(
                "Failed       : " + getFailedCount()
        );

        System.out.println(
                "Skipped      : " + getSkippedCount()
        );
    }
}