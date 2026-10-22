package com.testbuddy.utils;

public class TestResult {
    private String testName;
    private String status;
    private String errorMessage;

    public TestResult(String testName, String status, String errorMessage) {
        this.testName = testName;
        this.status = status;
        this.errorMessage = errorMessage;
    }

    public String getTestName() { return testName; }
    public String getStatus() { return status; }
    public String getErrorMessage() { return errorMessage; }
}