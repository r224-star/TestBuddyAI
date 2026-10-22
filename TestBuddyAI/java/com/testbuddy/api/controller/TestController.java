package com.testbuddy.api.controller;

import java.io.File;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.testbuddy.automation.SelfHealingDriver;

@RestController
@RequestMapping("/api/tests")
@CrossOrigin(origins = "*")
public class TestController {

    public static class HealingRequest {
        private String url;
        private String brokenLocator;
        private String hint;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getBrokenLocator() {
            return brokenLocator;
        }

        public void setBrokenLocator(String brokenLocator) {
            this.brokenLocator = brokenLocator;
        }

        public String getHint() {
            return hint;
        }

        public void setHint(String hint) {
            this.hint = hint;
        }
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        Map<String, String> status = new HashMap<>();
        status.put("status", "UP");
        status.put("service", "TestBuddyAI Automation Engine");
        return ResponseEntity.ok(status);
    }

    @PostMapping("/execute-healing-test")
    public ResponseEntity<Map<String, Object>> executeHealingTest(@RequestBody(required = false) HealingRequest request) {
        Map<String, Object> response = new HashMap<>();

        String targetUrl = (request != null && request.getUrl() != null && !request.getUrl().trim().isEmpty())
                ? request.getUrl().trim()
                : "https://www.saucedemo.com";

        String targetLocator = (request != null && request.getBrokenLocator() != null && !request.getBrokenLocator().trim().isEmpty())
                ? request.getBrokenLocator().trim()
                : "invalid_login_btn_id";

        String targetHint = (request != null && request.getHint() != null && !request.getHint().trim().isEmpty())
                ? request.getHint().trim()
                : "login-button";

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-setuid-sandbox");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-extensions");

        // Linux container me installed Chromium binaries auto-detect
        String[] linuxBinaries = {
            "/usr/bin/chromium",
            "/usr/bin/chromium-browser",
            "/usr/bin/google-chrome",
            "/usr/bin/google-chrome-stable"
        };

        for (String binaryPath : linuxBinaries) {
            File binaryFile = new File(binaryPath);
            if (binaryFile.exists() && binaryFile.canExecute()) {
                options.setBinary(binaryPath);
                break;
            }
        }

        // Driver path fallback for Linux environments
        File chromeDriverBin = new File("/usr/bin/chromedriver");
        if (chromeDriverBin.exists()) {
            System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
        }

        WebDriver rawDriver = null;
        SelfHealingDriver driver = null;

        try {
            rawDriver = new ChromeDriver(options);
            rawDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
            rawDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));

            driver = new SelfHealingDriver(rawDriver);
            driver.get(targetUrl);

            WebElement recoveredElement = driver.findElementWithHealing(By.id(targetLocator), targetHint);

            String tag = (recoveredElement != null) ? recoveredElement.getTagName() : "input";
            String screenshotBase64 = driver.getHealedScreenshotBase64();

            response.put("status", "SUCCESS");
            response.put("healed", true);
            response.put("targetUrl", targetUrl);
            response.put("simulatedBrokenLocator", targetLocator);
            response.put("semanticHint", targetHint);
            response.put("recoveredTag", tag);
            response.put("screenshot", screenshotBase64 != null ? screenshotBase64 : "");
            response.put("message", "Target located & self-healed dynamically on " + targetUrl);

            return ResponseEntity.ok(response);

        } catch (Throwable t) {
            t.printStackTrace();
            response.put("status", "FAILED");
            response.put("healed", false);
            response.put("targetUrl", targetUrl);
            response.put("message", "Execution error: " + (t.getMessage() != null ? t.getMessage() : "Failed to run headless session"));
            return ResponseEntity.status(500).body(response);

        } finally {
            if (driver != null) {
                try {
                    driver.quit();
                } catch (Exception ignored) {
                }
            } else if (rawDriver != null) {
                try {
                    rawDriver.quit();
                } catch (Exception ignored) {
                }
            }
        }
    }
}