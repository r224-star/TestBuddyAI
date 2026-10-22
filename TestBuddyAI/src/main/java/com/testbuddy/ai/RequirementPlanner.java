package com.testbuddy.ai;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

public class RequirementPlanner {

    private final AIAgent aiAgent;

    public RequirementPlanner() {
        this.aiAgent = new AIAgent();
    }

    public RequirementPlan plan(String requirement) throws Exception {

        if (requirement == null || requirement.isBlank()) {
            throw new IllegalArgumentException("Requirement cannot be empty");
        }

        String prompt =
                "You are a senior QA test planner. " +
                "Analyze the following software testing requirement and create a structured test strategy. " +
                "Return ONLY a valid JSON object. " +
                "Do not use markdown or explanation. " +
                "The JSON object must contain exactly these keys: " +
                "feature, objective, risk, testTypes, scenarios. " +
                "risk must be one of: LOW, MEDIUM, HIGH, CRITICAL. " +
                "testTypes must be a JSON array of strings. " +
                "scenarios must be a JSON array of strings. " +
                "Requirement: " + requirement;

        String rawResponse = aiAgent.askAI(prompt);

        String cleaned = rawResponse
                .replace("`json", "")
                .replace("`", "")
                .trim();

        JsonObject jsonObject =
                JsonParser.parseString(cleaned).getAsJsonObject();

        String feature = jsonObject.get("feature").getAsString();
        String objective = jsonObject.get("objective").getAsString();
        String risk = jsonObject.get("risk").getAsString();

        JsonArray testTypesJson = jsonObject.getAsJsonArray("testTypes");
        List<String> testTypes = new ArrayList<>();

        for (int i = 0; i < testTypesJson.size(); i++) {
            testTypes.add(testTypesJson.get(i).getAsString());
        }

        JsonArray scenariosJson = jsonObject.getAsJsonArray("scenarios");
        List<String> scenarios = new ArrayList<>();

        for (int i = 0; i < scenariosJson.size(); i++) {
            scenarios.add(scenariosJson.get(i).getAsString());
        }

        return new RequirementPlan(
                feature,
                objective,
                risk,
                testTypes,
                scenarios
        );
    }
}