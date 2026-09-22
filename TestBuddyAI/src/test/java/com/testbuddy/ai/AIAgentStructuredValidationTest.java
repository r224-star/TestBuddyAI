package com.testbuddy.ai;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AIAgentStructuredValidationTest {

    @Test
    void shouldRejectBlankId() {

        StructuredTestCase testCase = new StructuredTestCase(
                "",
                "Successful Login",
                "POSITIVE",
                "HIGH",
                List.of("Registered user exists"),
                List.of("Open login page"),
                "User is redirected to dashboard"
        );

        assertFalse(StructuredTestCaseValidator.validate(testCase));
    }

    @Test
    void shouldRejectBlankTitle() {

        StructuredTestCase testCase = new StructuredTestCase(
                "TC_LOGIN_006",
                "   ",
                "POSITIVE",
                "HIGH",
                List.of("Registered user exists"),
                List.of("Open login page"),
                "User is redirected to dashboard"
        );

        assertFalse(StructuredTestCaseValidator.validate(testCase));
    }

    @Test
    void shouldRejectNullTestCase() {

        assertFalse(
                StructuredTestCaseValidator.validate(null)
        );
    }
}