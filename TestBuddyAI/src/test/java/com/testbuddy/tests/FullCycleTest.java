package com.testbuddy.tests;

import com.testbuddy.ai.AIAgent;
import com.testbuddy.automation.TestCaseDAO;
import com.testbuddy.utils.TestCase;
import org.testng.annotations.Test;
import java.util.List;

public class FullCycleTest {

    @Test
    public void generateSaveAndReadTestCases() throws Exception {
        // 1. AI se generate karo
        AIAgent agent = new AIAgent();
        List<TestCase> generated = agent.generateTestCases("User registration functionality");

        // 2. Database me save karo
        TestCaseDAO dao = new TestCaseDAO();
        dao.saveTestCases(generated);

        // 3. Wapas database se padho
        List<TestCase> fromDb = dao.getAllTestCases();

        // 4. Print karo verify karne ke liye
        System.out.println("--- Test cases from database ---");
        for (TestCase tc : fromDb) {
            System.out.println(tc);
        }
    }
}