package com.testbuddy.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.testbuddy.ai.TestingOrchestrator.TestExecutionSummary;
import com.testbuddy.api.dto.TestRunRequest;
import com.testbuddy.api.dto.TestRunResponse;
import com.testbuddy.api.service.TestExecutionService;
import com.testbuddy.automation.ExtentReportManager;
import com.testbuddy.automation.SelfHealingDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174"})
@RequestMapping("/api/tests")
public class TestController {

    private final TestExecutionService testExecutionService;

    public TestController(TestExecutionService testExecutionService) {
        this.testExecutionService = testExecutionService;
    }

    @PostMapping("/run")
    public ResponseEntity<TestRunResponse> runTest(@RequestBody TestRunRequest request) {
        if (request == null) {
            return ResponseEntity.badRequest()
                    .body(new TestRunResponse("FAILED", "Request body is required.", 0, 0, 0, 0));
        }

        if (request.getRequirement() == null || request.getRequirement().isBlank()) {
            return ResponseEntity.badRequest()
                    .body(new TestRunResponse("FAILED", "Requirement is required.", 0, 0, 0, 0));
        }

        if (request.getTestClasses() == null || request.getTestClasses().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new TestRunResponse("FAILED", "At least one test class is required.", 0, 0, 0, 0));
        }

        try {
            TestExecutionSummary summary = testExecutionService.runTest(
                    request.getRequirement(),
                    request.getTestClasses()
            );

            return ResponseEntity.ok(
                    new TestRunResponse(
                            "COMPLETED",
                            "Test execution completed successfully.",
                            summary.getTotal(),
                            summary.getPassed(),
                            summary.getFailed(),
                            summary.getSkipped()
                    )
            );
        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = e.getMessage() != null && !e.getMessage().isBlank()
                    ? e.getMessage()
                    : e.getClass().getName();

            return ResponseEntity.internalServerError()
                    .body(new TestRunResponse("FAILED", errorMessage, 0, 0, 0, 0));
        }
    }

    @PostMapping("/execute-healing-test")
    public ResponseEntity<Map<String, Object>> runSelfHealingTest(
            @RequestBody(required = false) Map<String, String> payload) {

        String targetUrl = (payload != null && payload.containsKey("url"))
                ? payload.get("url") : "https://www.saucedemo.com";
        String fallbackHint = (payload != null && payload.containsKey("hint"))
                ? payload.get("hint") : "login-button";

        Map<String, Object> response = new HashMap<>();

        ExtentTest testReport = ExtentReportManager.createTest(
                "Autonomous Self-Healing Test",
                "Verifies dynamic DOM recovery for broken locators on: " + targetUrl
        );

        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new", "--disable-gpu", "--no-sandbox");

        WebDriver baseDriver = new ChromeDriver(options);
        SelfHealingDriver healingDriver = new SelfHealingDriver(baseDriver);

        try {
            testReport.log(Status.INFO, "Launching headless browser and navigating to " + targetUrl);
            healingDriver.getDriver().get(targetUrl);

            testReport.log(Status.WARNING, "Simulating failure using broken locator: By.id('broken_login_button_id')");
            By brokenLocator = By.id("broken_login_button_id");

            testReport.log(Status.INFO, "Engaging Autonomous Self-Healing fallback engine with hint: '" + fallbackHint + "'");
            WebElement recoveredElement = healingDriver.findElementWithHealing(brokenLocator, fallbackHint);

            testReport.log(Status.PASS, "Successfully recovered element! Tag name: <" + recoveredElement.getTagName() + ">");

            response.put("status", "SUCCESS");
            response.put("healed", true);
            response.put("recoveredTag", recoveredElement.getTagName());
            response.put("reportPath", "reports/SelfHealingTestReport.html");
            response.put("message", "Broken locator was autonomously healed and visual report updated!");
        } catch (Exception ex) {
            testReport.log(Status.FAIL, "Self-healing test failed: " + ex.getMessage());
            response.put("status", "FAILED");
            response.put("error", ex.getMessage());
        } finally {
            baseDriver.quit();
            ExtentReportManager.flush();
        }

        return ResponseEntity.ok(response);
    }
}