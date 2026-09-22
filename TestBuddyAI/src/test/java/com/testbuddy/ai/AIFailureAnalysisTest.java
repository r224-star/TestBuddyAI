package com.testbuddy.ai;

import com.testbuddy.utils.FailureInfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AIFailureAnalysisTest {

    @Test
    void shouldAnalyzeFailureWithAI() throws Exception {

        FailureInfo failure = new FailureInfo(
                "validLoginTest",
                "org.openqa.selenium.NoSuchElementException",
                "Unable to locate element: username"
        );

        AIFailureAnalyzer analyzer = new AIFailureAnalyzer();

        FailureAnalysisReport report = analyzer.analyze(failure);

        assertNotNull(report);
        assertNotNull(report.getRootCause());
        assertNotNull(report.getCategory());
        assertTrue(report.getConfidence() >= 0);
        assertTrue(report.getConfidence() <= 100);
        assertNotNull(report.getRecommendedFix());
    }
}
