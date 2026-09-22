package com.testbuddy.ai;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StructuredTestCaseValidatorTest {

    @Test
    void shouldAcceptValidStructuredTestCase() {

        StructuredTestCase testCase = new StructuredTestCase(
                "TC_LOGIN_001",
                "Successful Login",
                "POSITIVE",
                "HIGH",
                List.of("Registered user exists"),
                List.of(
                        "Open login page",
                        "Enter valid username",
                        "Enter valid password",
                        "Click Login"
                ),
                "User is redirected to dashboard"
        );

        assertTrue(
                StructuredTestCaseValidator.validate(testCase)
        );
    }

    @Test
    void shouldRejectTestCaseWithInvalidType() {

        StructuredTestCase testCase = new StructuredTestCase(
                "TC_LOGIN_002",
                "Invalid Type",
                "INVALID",
                "HIGH",
                List.of("Registered user exists"),
                List.of("Open login page"),
                "Login result is displayed"
        );

        assertFalse(
                StructuredTestCaseValidator.validate(testCase)
        );
    }

    @Test
    void shouldRejectTestCaseWithInvalidPriority() {

        StructuredTestCase testCase = new StructuredTestCase(
                "TC_LOGIN_003",
                "Invalid Priority",
                "POSITIVE",
                "URGENT",
                List.of("Registered user exists"),
                List.of("Open login page"),
                "Login page is displayed"
        );

        assertFalse(
                StructuredTestCaseValidator.validate(testCase)
        );
    }

    @Test
    void shouldRejectTestCaseWithEmptySteps() {

        StructuredTestCase testCase = new StructuredTestCase(
                "TC_LOGIN_004",
                "Empty Steps",
                "POSITIVE",
                "MEDIUM",
                List.of("Registered user exists"),
                List.of(),
                "User can login"
        );

        assertFalse(
                StructuredTestCaseValidator.validate(testCase)
        );
    }

    @Test
    void shouldRejectTestCaseWithBlankExpectedResult() {

        StructuredTestCase testCase = new StructuredTestCase(
                "TC_LOGIN_005",
                "Blank Expected Result",
                "POSITIVE",
                "MEDIUM",
                List.of("Registered user exists"),
                List.of("Open login page"),
                "   "
        );

        assertFalse(
                StructuredTestCaseValidator.validate(testCase)
        );
    }
}