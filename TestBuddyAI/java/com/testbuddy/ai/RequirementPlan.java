package com.testbuddy.ai;

import java.util.List;

public class RequirementPlan {

    private final String feature;
    private final String objective;
    private final String risk;
    private final List<String> testTypes;
    private final List<String> scenarios;

    public RequirementPlan(
            String feature,
            String objective,
            String risk,
            List<String> testTypes,
            List<String> scenarios) {

        this.feature = feature;
        this.objective = objective;
        this.risk = risk;
        this.testTypes = List.copyOf(testTypes);
        this.scenarios = List.copyOf(scenarios);
    }

    public String getFeature() {
        return feature;
    }

    public String getObjective() {
        return objective;
    }

    public String getRisk() {
        return risk;
    }

    public List<String> getTestTypes() {
        return testTypes;
    }

    public List<String> getScenarios() {
        return scenarios;
    }
}