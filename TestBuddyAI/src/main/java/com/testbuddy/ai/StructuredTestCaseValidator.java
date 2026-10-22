package com.testbuddy.ai;

public class StructuredTestCaseValidator {

    private StructuredTestCaseValidator() {
    }

    public static boolean validate(StructuredTestCase testCase) {

        if (testCase == null) {
            return false;
        }

        if (isBlank(testCase.getId())) {
            return false;
        }

        if (isBlank(testCase.getTitle())) {
            return false;
        }

        if (!isValidType(testCase.getType())) {
            return false;
        }

        if (!isValidPriority(testCase.getPriority())) {
            return false;
        }

        if (testCase.getPreconditions() == null) {
            return false;
        }

        if (testCase.getSteps() == null || testCase.getSteps().isEmpty()) {
            return false;
        }

        if (isBlank(testCase.getExpectedResult())) {
            return false;
        }

        return true;
    }

    private static boolean isValidType(String type) {
        return "POSITIVE".equals(type)
                || "NEGATIVE".equals(type)
                || "EDGE".equals(type);
    }

    private static boolean isValidPriority(String priority) {
        return "LOW".equals(priority)
                || "MEDIUM".equals(priority)
                || "HIGH".equals(priority)
                || "CRITICAL".equals(priority);
    }

    private static boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}