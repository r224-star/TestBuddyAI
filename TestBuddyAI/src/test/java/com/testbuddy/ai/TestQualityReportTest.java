package com.testbuddy.ai;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestQualityReportTest {

    @Test
    void shouldCreateQualityReport() {

        TestQualityReport report = new TestQualityReport(
                90,
                "PASS",
                List.of(),
                List.of("Add an edge-case scenario")
        );

        assertEquals(90, report.getScore());
        assertEquals("PASS", report.getStatus());
        assertTrue(report.getIssues().isEmpty());
        assertEquals(1, report.getSuggestions().size());
    }

    @Test
    void shouldPreserveQualityIssues() {

        TestQualityReport report = new TestQualityReport(
                45,
                "FAIL",
                List.of(
                        "Expected result is not specific",
                        "Missing negative scenario"
                ),
                List.of(
                        "Make the expected result measurable"
                )
        );

        assertEquals(45, report.getScore());
        assertEquals("FAIL", report.getStatus());
        assertEquals(2, report.getIssues().size());
        assertEquals(1, report.getSuggestions().size());
    }
}
