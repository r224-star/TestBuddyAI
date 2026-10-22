package com.testbuddy.ai;

import java.util.List;
//import com.testbuddy.tests.StructuredTestCase;

public class StructuredTestCase {

    private final String id;
    private final String title;
    private final String type;
    private final String priority;
    private final List<String> preconditions;
    private final List<String> steps;
    private final String expectedResult;

    public StructuredTestCase(
            String id,
            String title,
            String type,
            String priority,
            List<String> preconditions,
            List<String> steps,
            String expectedResult) {

        this.id = id;
        this.title = title;
        this.type = type;
        this.priority = priority;
        this.preconditions = List.copyOf(preconditions);
        this.steps = List.copyOf(steps);
        this.expectedResult = expectedResult;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getPriority() {
        return priority;
    }

    public List<String> getPreconditions() {
        return preconditions;
    }

    public List<String> getSteps() {
        return steps;
    }

    public String getExpectedResult() {
        return expectedResult;
    }
}
