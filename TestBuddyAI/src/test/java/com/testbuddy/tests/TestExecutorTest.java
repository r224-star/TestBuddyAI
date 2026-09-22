package com.testbuddy.tests;

import com.testbuddy.automation.TestExecutor;
import org.testng.annotations.Test;

import java.util.Arrays;

public class TestExecutorTest {

    @Test
    public void runSelectedTests() throws Exception {
        TestExecutor executor = new TestExecutor();
        executor.runTestsByClassName(Arrays.asList(
                "com.testbuddy.tests.FirstTest",
                "com.testbuddy.tests.LoginTest"
        ));
    }
}