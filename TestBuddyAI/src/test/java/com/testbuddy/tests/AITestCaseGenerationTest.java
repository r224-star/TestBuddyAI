package com.testbuddy.tests;

import com.testbuddy.ai.AIAgent;
import com.testbuddy.utils.TestCase;
import org.testng.annotations.Test;
import java.util.List;

public class AITestCaseGenerationTest {

    @Test
    public void generateTestCasesFromRequirement() throws Exception {
        AIAgent agent = new AIAgent();
        List<TestCase> testCases = agent.generateTestCases("User login functionality with username and password");

        for (TestCase tc : testCases) {
            System.out.println(tc);
        }
    }
}