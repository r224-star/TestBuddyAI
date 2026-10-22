package com.testbuddy.ai;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

public class AITestQualityValidator {

    private final AIAgent aiAgent;

    public AITestQualityValidator() {
        this.aiAgent = new AIAgent();
    }

    public TestQualityReport validate(StructuredTestCase testCase) throws Exception {

        if (testCase == null) {
            throw new IllegalArgumentException("Test case cannot be null");
        }

        String prompt =
                "You are a senior QA automation expert. " +
                "Evaluate the quality of this test case. " +
                "Return ONLY a valid JSON object with exactly these keys: " +
                "score, status, issues, suggestions. " +
                "score must be an integer from 0 to 100. " +
                "status must be PASS if score is 70 or higher, otherwise FAIL. " +
                "issues must be a JSON array of strings. " +
                "suggestions must be a JSON array of strings. " +
                "Evaluate clarity, completeness, test steps, expected result, " +
                "preconditions, coverage and test usefulness. " +
                "Test case ID: " + testCase.getId() +
                ", Title: " + testCase.getTitle() +
                ", Type: " + testCase.getType() +
                ", Priority: " + testCase.getPriority() +
                ", Preconditions: " + testCase.getPreconditions() +
                ", Steps: " + testCase.getSteps() +
                ", Expected Result: " + testCase.getExpectedResult();

        String rawResponse = aiAgent.askAI(prompt);

        String cleaned = rawResponse
                .replace("`json", "")
                .replace("`", "")
                .trim();

        JsonObject obj =
                JsonParser.parseString(cleaned).getAsJsonObject();

        int score = obj.get("score").getAsInt();
        String status = obj.get("status").getAsString();

        JsonArray issuesJson = obj.getAsJsonArray("issues");
        List<String> issues = new ArrayList<>();

        for (int i = 0; i < issuesJson.size(); i++) {
            issues.add(issuesJson.get(i).getAsString());
        }

        JsonArray suggestionsJson = obj.getAsJsonArray("suggestions");
        List<String> suggestions = new ArrayList<>();

        for (int i = 0; i < suggestionsJson.size(); i++) {
            suggestions.add(suggestionsJson.get(i).getAsString());
        }

        return new TestQualityReport(
                score,
                status,
                issues,
                suggestions
        );
    }
}
