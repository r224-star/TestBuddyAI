package com.testbuddy.ai;

import java.util.ArrayList;
import java.util.List;

public class TestQualityValidator {

    private TestQualityValidator() {
    }

    public static TestQualityReport validate(StructuredTestCase testCase) {

        List<String> issues = new ArrayList<>();
        List<String> suggestions = new ArrayList<>();

        int score = 100;

        if (testCase == null) {
            return new TestQualityReport(
                    0,
                    "FAIL",
                    List.of("Test case is null"),
                    List.of("Provide a valid test case")
            );
        }

        if (testCase.getTitle() == null || testCase.getTitle().isBlank()) {
            score -= 20;
            issues.add("Test case title is missing");
            suggestions.add("Add a clear and specific test case title");
        }

        if (testCase.getSteps() == null || testCase.getSteps().size() < 2) {
            score -= 25;
            issues.add("Insufficient test steps");
            suggestions.add("Add detailed and complete test steps");
        }

        if (testCase.getExpectedResult() == null
                || testCase.getExpectedResult().isBlank()
                || testCase.getExpectedResult().equalsIgnoreCase("Login works")) {

            score -= 25;
            issues.add("Expected result is not specific");
            suggestions.add("Make the expected result measurable and specific");
        }

        if (testCase.getPreconditions() == null
                || testCase.getPreconditions().isEmpty()) {

            score -= 15;
            issues.add("Missing preconditions");
            suggestions.add("Define required preconditions before execution");
        }

        if ("POSITIVE".equals(testCase.getType())) {
            suggestions.add("Consider adding negative and edge-case scenarios");
        }

        if (score < 0) {
            score = 0;
        }

        String status = score >= 70 ? "PASS" : "FAIL";

        return new TestQualityReport(
                score,
                status,
                issues,
                suggestions
        );
    }
}
