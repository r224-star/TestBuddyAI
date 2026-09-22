package com.testbuddy.ai;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AIAgentStructuredTest {

    @Test
    void shouldGenerateStructuredTestCasesFromRequirement() throws Exception {

        AIAgent aiAgent = new AIAgent();

        List<StructuredTestCase> testCases =
                aiAgent.generateStructuredTestCases(
                        "Test login functionality with valid and invalid credentials"
                );

        assertNotNull(testCases);
        assertFalse(testCases.isEmpty());
        assertTrue(testCases.size() >= 3);

        StructuredTestCase testCase = testCases.get(0);

        assertNotNull(testCase.getId());
        assertNotNull(testCase.getTitle());
        assertNotNull(testCase.getType());
        assertNotNull(testCase.getPriority());

        assertNotNull(testCase.getPreconditions());
        assertNotNull(testCase.getSteps());
        assertFalse(testCase.getSteps().isEmpty());

        assertNotNull(testCase.getExpectedResult());
        assertFalse(testCase.getExpectedResult().isBlank());
    }
}
