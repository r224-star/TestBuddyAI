package com.testbuddy.ai;

import com.testbuddy.automation.DefectReportGenerator;
import com.testbuddy.automation.TestCaseDAO;
import com.testbuddy.automation.TestExecutionListener;
import com.testbuddy.automation.TestExecutor;
import com.testbuddy.utils.TestCase;

import java.util.List;

public class TestingOrchestrator {

    public TestExecutionSummary runFullCycle(
            String requirement,
            List<String> testClassesToRun) throws Exception {

        System.out.println(
                "========== STEP 1: AI analyzing requirement =========="
        );

        System.out.println(
                "Requirement: " + requirement
        );

        // ==============================
        // STEP 2 - AI TEST CASE GENERATION
        // ==============================

        System.out.println(
                "\n========== STEP 2: AI generating test cases =========="
        );

        AIAgent agent = new AIAgent();

        List<TestCase> generatedTestCases =
                agent.generateTestCases(requirement);

        for (TestCase tc : generatedTestCases) {
            System.out.println(tc);
        }

        // ==============================
        // STEP 3 - SAVE TEST CASES
        // ==============================

        System.out.println(
                "\n========== STEP 3: Saving test cases to database =========="
        );

        TestCaseDAO testCaseDAO = new TestCaseDAO();

        testCaseDAO.saveTestCases(
                generatedTestCases
        );

        // ==============================
        // STEP 4 - EXECUTE TESTS
        // ==============================

        System.out.println(
                "\n========== STEP 4: Executing automation tests =========="
        );

        TestExecutor executor = new TestExecutor();

        executor.runTestsByClassName(
                testClassesToRun
        );

        // ==============================
        // GET EXECUTION SUMMARY
        // ==============================

        int total =
                TestExecutionListener.getTotalCount();

        int passed =
                TestExecutionListener.getPassedCount();

        int failed =
                TestExecutionListener.getFailedCount();

        int skipped =
                TestExecutionListener.getSkippedCount();

        // ==============================
        // STEP 5 - FAILURE ANALYSIS
        // ==============================

        System.out.println(
                "\n========== STEP 5: Analyzing failures (if any) =========="
        );

        if (TestExecutionListener.getFailures().isEmpty()) {

            System.out.println(
                    "No failures found. All tests passed!"
            );

        } else {

            System.out.println(
                    TestExecutionListener.getFailures().size()
                            + " failure(s) found. Generating defect report..."
            );
        }

        // ==============================
        // STEP 6 - DEFECT REPORT
        // ==============================

        System.out.println(
                "\n========== STEP 6: Generating defect report =========="
        );

        DefectReportGenerator reportGenerator =
                new DefectReportGenerator();

        reportGenerator.generateReport(
                TestExecutionListener.getFailures(),
                "reports/DefectReport.html"
        );

        System.out.println(
                "\n========== CYCLE COMPLETE =========="
        );

        System.out.println(
                "Check reports/ExtentReport.html for test results"
        );

        System.out.println(
                "Check reports/DefectReport.html for failure analysis"
        );

        // ==============================
        // RETURN RESULT
        // ==============================

        return new TestExecutionSummary(
                total,
                passed,
                failed,
                skipped
        );
    }

    // ==========================================
    // EXECUTION SUMMARY
    // ==========================================

    public static class TestExecutionSummary {

        private final int total;
        private final int passed;
        private final int failed;
        private final int skipped;

        public TestExecutionSummary(
                int total,
                int passed,
                int failed,
                int skipped) {

            this.total = total;
            this.passed = passed;
            this.failed = failed;
            this.skipped = skipped;
        }

        public int getTotal() {
            return total;
        }

        public int getPassed() {
            return passed;
        }

        public int getFailed() {
            return failed;
        }

        public int getSkipped() {
            return skipped;
        }
    }
}