package com.testbuddy.api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.testbuddy.ai.TestingOrchestrator;
import com.testbuddy.ai.TestingOrchestrator.TestExecutionSummary;

@Service
public class TestExecutionService {

    private final TestingOrchestrator testingOrchestrator;

    public TestExecutionService() {
        this.testingOrchestrator =
                new TestingOrchestrator();
    }

    public TestExecutionSummary runTest(
            String requirement,
            List<String> testClasses) throws Exception {

        return testingOrchestrator.runFullCycle(
                requirement,
                testClasses
        );
    }
}