package com.testbuddy.ai;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RequirementPlannerTest {

    @Test
    void shouldCreateStructuredPlanFromRequirement() throws Exception {
        RequirementPlanner planner = new RequirementPlanner();

        RequirementPlan plan = planner.plan(
                "Test the login functionality with valid and invalid credentials"
        );

        assertNotNull(plan);
        assertNotNull(plan.getFeature());
        assertNotNull(plan.getObjective());
        assertNotNull(plan.getRisk());

        assertFalse(plan.getTestTypes().isEmpty());
        assertFalse(plan.getScenarios().isEmpty());
    }
}
