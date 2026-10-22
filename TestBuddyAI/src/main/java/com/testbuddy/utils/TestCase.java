package com.testbuddy.utils;

public class TestCase {
    private String id;
    private String title;
    private String steps;
    private String expectedResult;

    public TestCase(String id, String title, String steps, String expectedResult) {
        this.id = id;
        this.title = title;
        this.steps = steps;
        this.expectedResult = expectedResult;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getSteps() { return steps; }
    public String getExpectedResult() { return expectedResult; }

    @Override
    public String toString() {
        return "TestCase{" + id + " - " + title + " | Steps: " + steps + " | Expected: " + expectedResult + "}";
    }
}