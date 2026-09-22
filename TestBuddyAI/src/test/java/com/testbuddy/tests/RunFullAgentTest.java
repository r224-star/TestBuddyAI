package com.testbuddy.tests;

import com.testbuddy.ai.TestingOrchestrator;
import org.testng.annotations.Test;
import java.util.Arrays;

public class RunFullAgentTest {

    @Test
    public void runAutonomousAgent() throws Exception {
        TestingOrchestrator orchestrator = new TestingOrchestrator();

        orchestrator.runFullCycle(
                "User login functionality with valid and invalid credentials",
                Arrays.asList(
                        "com.testbuddy.tests.FirstTest",
                        "com.testbuddy.tests.LoginTest"
                )
        );
    }
}