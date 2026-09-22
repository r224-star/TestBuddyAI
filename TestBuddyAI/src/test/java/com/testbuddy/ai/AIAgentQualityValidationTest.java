package com.testbuddy.ai;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AIAgentQualityValidationTest {

    @Test
    void shouldAnalyzeTestCaseQualityWithAI() throws Exception {

        StructuredTestCase testCase = new StructuredTestCase(
                "TC-001",
                "Login with valid credentials",
                "POSITIVE",
                "HIGH",
                List.of("User account exists"),
                List.of(
                        "Open login page",
                        "Enter valid username and password",
                        "Click login"
                ),
                "User is successfully logged in and dashboard is displayed"
        );

        TestQualityReport report =
                new AITestQualityValidator().validate(testCase);

        assertNotNull(report);
        assertTrue(report.getScore() >= 0);
        assertTrue(report.getScore() <= 100);
        assertNotNull(report.getStatus());
        assertNotNull(report.getIssues());
        assertNotNull(report.getSuggestions());
    }
}
