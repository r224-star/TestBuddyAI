package com.testbuddy.ai;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.testbuddy.utils.TestCase;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class AIAgent {

	private static final String API_URL =
		    "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash:generateContent";

	
    public String askAI(String prompt) throws Exception {
        String apiKey = System.getenv("GEMINI_API_KEY");
        if (apiKey == null || apiKey.isEmpty()) {
            throw new RuntimeException("GEMINI_API_KEY environment variable not set!");
        }

        JsonObject requestBody = new JsonObject();
        JsonArray contents = new JsonArray();
        JsonObject content = new JsonObject();
        JsonArray parts = new JsonArray();
        JsonObject part = new JsonObject();
        part.addProperty("text", prompt);
        parts.add(part);
        content.add("parts", parts);
        contents.add(content);
        requestBody.add("contents", contents);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + "?key=" + apiKey))
                .header("content-type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("API call failed: " + response.statusCode() + " - " + response.body());
        }

        JsonObject responseJson = JsonParser.parseString(response.body()).getAsJsonObject();
        return responseJson.getAsJsonArray("candidates")
                .get(0).getAsJsonObject()
                .getAsJsonObject("content")
                .getAsJsonArray("parts")
                .get(0).getAsJsonObject()
                .get("text").getAsString();
    }

    public List<TestCase> generateTestCases(String requirement) throws Exception {
        String prompt = "You are a QA engineer. For the following requirement, generate 3 to 5 test cases. "
                + "Return ONLY a valid JSON array, no explanation, no markdown, no extra text. "
                + "Each item must have exactly these keys: id, title, steps, expectedResult. "
                + "Requirement: " + requirement;

        String rawResponse = askAI(prompt);
        String cleaned = rawResponse.replace("```json", "").replace("```", "").trim();

        JsonArray jsonArray = JsonParser.parseString(cleaned).getAsJsonArray();
        List<TestCase> testCases = new ArrayList<>();

        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject obj = jsonArray.get(i).getAsJsonObject();
            testCases.add(new TestCase(
                    obj.get("id").getAsString(),
                    obj.get("title").getAsString(),
                    obj.get("steps").getAsString(),
                    obj.get("expectedResult").getAsString()
            ));
        }

        return testCases;
    }

    public String analyzeFailure(com.testbuddy.utils.FailureInfo failure) throws Exception {
        String prompt = "You are a senior QA engineer. A test failed. "
                + "Test Name: " + failure.getTestName() + ". "
                + "Error Message: " + failure.getErrorMessage() + ". "
                + "Stack Trace: " + failure.getStackTrace() + ". "
                + "Give: 1) Probable root cause 2) Suggested fix, in plain text, max 5 lines.";

        return askAI(prompt);
    }
    public List<StructuredTestCase> generateStructuredTestCases(String requirement) throws Exception {

        if (requirement == null || requirement.isBlank()) {
            throw new IllegalArgumentException("Requirement cannot be empty");
        }

        String prompt =
                "You are a senior QA automation engineer. " +
                "For the following software testing requirement, generate 3 to 5 structured test cases. " +
                "Return ONLY a valid JSON array. " +
                "Do not use markdown or explanation. " +
                "Each test case must contain exactly these keys: " +
                "id, title, type, priority, preconditions, steps, expectedResult. " +
                "type must be one of: POSITIVE, NEGATIVE, EDGE. " +
                "priority must be one of: LOW, MEDIUM, HIGH, CRITICAL. " +
                "preconditions must be a JSON array of strings. " +
                "steps must be a JSON array of strings. " +
                "Requirement: " + requirement;

        String rawResponse = askAI(prompt);

        String cleaned = rawResponse
                .replace("```json", "")
                .replace("```", "")
                .trim();

        JsonArray jsonArray =
                JsonParser.parseString(cleaned).getAsJsonArray();

        List<StructuredTestCase> testCases = new ArrayList<>();

        for (int i = 0; i < jsonArray.size(); i++) {

            JsonObject obj =
                    jsonArray.get(i).getAsJsonObject();

            JsonArray preconditionsJson =
                    obj.getAsJsonArray("preconditions");

            List<String> preconditions = new ArrayList<>();

            for (int j = 0; j < preconditionsJson.size(); j++) {
                preconditions.add(
                        preconditionsJson.get(j).getAsString()
                );
            }

            JsonArray stepsJson =
                    obj.getAsJsonArray("steps");

            List<String> steps = new ArrayList<>();

            for (int j = 0; j < stepsJson.size(); j++) {
                steps.add(
                        stepsJson.get(j).getAsString()
                );
            }

            StructuredTestCase testCase =
                    new StructuredTestCase(
                            obj.get("id").getAsString(),
                            obj.get("title").getAsString(),
                            obj.get("type").getAsString(),
                            obj.get("priority").getAsString(),
                            preconditions,
                            steps,
                            obj.get("expectedResult").getAsString()
                    );

            if (!StructuredTestCaseValidator.validate(testCase)) {
                throw new IllegalArgumentException(
                        "AI generated invalid test case: " + testCase.getId()
                );
            }

            testCases.add(testCase);
        }

        return testCases;
    }
}