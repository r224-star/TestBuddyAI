package com.testbuddy.ai;

import java.util.List;

public class TestQualityReport {

    private final int score;
    private final String status;
    private final List<String> issues;
    private final List<String> suggestions;

    public TestQualityReport(
            int score,
            String status,
            List<String> issues,
            List<String> suggestions) {

        this.score = score;
        this.status = status;
        this.issues = List.copyOf(issues);
        this.suggestions = List.copyOf(suggestions);
    }

    public int getScore() {
        return score;
    }

    public String getStatus() {
        return status;
    }

    public List<String> getIssues() {
        return issues;
    }

    public List<String> getSuggestions() {
        return suggestions;
    }
}
