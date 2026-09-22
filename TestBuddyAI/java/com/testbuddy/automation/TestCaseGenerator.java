package com.testbuddy.automation;

import com.testbuddy.utils.TestCase;
import java.util.ArrayList;
import java.util.List;

public class TestCaseGenerator {

    public List<TestCase> generateLoginTestCases() {
        List<TestCase> testCases = new ArrayList<>();

        testCases.add(new TestCase("TC001", "Valid Login",
                "Enter valid username and password, click login",
                "User redirected to inventory page"));

        testCases.add(new TestCase("TC002", "Invalid Password",
                "Enter valid username and wrong password, click login",
                "Error message shown"));

        testCases.add(new TestCase("TC003", "Empty Username",
                "Leave username blank, enter password, click login",
                "Error message: username required"));

        return testCases;
    }
}