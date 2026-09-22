package com.testbuddy.api.dto;

public class TestRunResponse {

    private String status;
    private String message;

    private int total;
    private int passed;
    private int failed;
    private int skipped;

    public TestRunResponse() {
    }

    public TestRunResponse(
            String status,
            String message,
            int total,
            int passed,
            int failed,
            int skipped) {

        this.status = status;
        this.message = message;

        this.total = total;
        this.passed = passed;
        this.failed = failed;
        this.skipped = skipped;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPassed() {
        return passed;
    }

    public void setPassed(int passed) {
        this.passed = passed;
    }

    public int getFailed() {
        return failed;
    }

    public void setFailed(int failed) {
        this.failed = failed;
    }

    public int getSkipped() {
        return skipped;
    }

    public void setSkipped(int skipped) {
        this.skipped = skipped;
    }
}