package com.testbuddy.ai;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.testbuddy.utils.FailureInfo;

public class AIFailureAnalyzer {

    private final AIAgent aiAgent;

    public AIFailureAnalyzer() {
        this.aiAgent = new AIAgent();
    }

    public FailureAnalysisReport analyze(FailureInfo failure) throws Exception {

        if (failure == null) {
            throw new IllegalArgumentException("Failure cannot be null");
        }

        String prompt =
                "You are a senior QA automation engineer. " +
                "Analyze the following automated test failure. " +
                "Return ONLY a valid JSON object with exactly these keys: " +
                "rootCause, category, confidence, recommendedFix. " +
                "category must describe the failure type. " +
                "confidence must be an integer from 0 to 100. " +
                "Be concise and practical. " +
                "Test: " + failure.getTestName() +
                ", Message: " + failure.getErrorMessage() +
                ", Stack Trace: " + failure.getStackTrace();

        String rawResponse = aiAgent.askAI(prompt);

        String cleaned = rawResponse
                .replace("```json", "")
                .replace("```", "")
                .trim();

        JsonObject obj =
                JsonParser.parseString(cleaned).getAsJsonObject();

        return new FailureAnalysisReport(
                obj.get("rootCause").getAsString(),
                obj.get("category").getAsString(),
                obj.get("confidence").getAsInt(),
                obj.get("recommendedFix").getAsString()
        );
    }
}