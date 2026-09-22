package com.testbuddy.ai;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestQualityValidatorTest {

    @Test
    void shouldRejectWeakTestCase() {

        StructuredTestCase testCase = new StructuredTestCase(
                "TC-001",
                "Login test",
                "POSITIVE",
                "HIGH",
                List.of(),
                List.of("Enter username"),
                "Login works"
        );

        TestQualityReport report =
                TestQualityValidator.validate(testCase);

        assertTrue(report.getScore() < 70);
        assertEquals("FAIL", report.getStatus());
        assertFalse(report.getIssues().isEmpty());
        assertFalse(report.getSuggestions().isEmpty());
    }
}
