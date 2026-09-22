package com.testbuddy.tests;

import com.testbuddy.automation.TestCaseGenerator;
import com.testbuddy.utils.TestCase;
import org.testng.annotations.Test;
import java.util.List;

public class TestCaseGeneratorTest {

    @Test
    public void printGeneratedTestCases() {
        TestCaseGenerator generator = new TestCaseGenerator();
        List<TestCase> testCases = generator.generateLoginTestCases();

        for (TestCase tc : testCases) {
            System.out.println(tc);
        }
    }
}