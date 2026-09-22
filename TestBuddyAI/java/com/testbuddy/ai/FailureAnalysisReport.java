package com.testbuddy.ai;

public class FailureAnalysisReport {

    private final String rootCause;
    private final String category;
    private final int confidence;
    private final String recommendedFix;

    public FailureAnalysisReport(
            String rootCause,
            String category,
            int confidence,
            String recommendedFix) {

        this.rootCause = rootCause;
        this.category = category;
        this.confidence = confidence;
        this.recommendedFix = recommendedFix;
    }

    public String getRootCause() {
        return rootCause;
    }

    public String getCategory() {
        return category;
    }

    public int getConfidence() {
        return confidence;
    }

    public String getRecommendedFix() {
        return recommendedFix;
    }
}
