package com.testbuddy.api.dto;

import java.util.List;

public class TestRunRequest {

    private String requirement;
    private List<String> testClasses;

    public TestRunRequest() {
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public List<String> getTestClasses() {
        return testClasses;
    }

    public void setTestClasses(List<String> testClasses) {
        this.testClasses = testClasses;
    }
}