package com.testbuddy.automation;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SelfHealingDriver {

    private WebDriver driver;
    private String healedScreenshotBase64;

    public SelfHealingDriver(WebDriver driver) {
        this.driver = driver;
        this.healedScreenshotBase64 = null;
    }

    public void get(String url) {
        if (driver != null) {
            driver.get(url);
        }
    }

    public void quit() {
        if (driver != null) {
            driver.quit();
        }
    }

    public WebDriver getWrappedDriver() {
        return driver;
    }

    public String getHealedScreenshotBase64() {
        return healedScreenshotBase64;
    }

    /**
     * Primary locator try karta hai, fail hone par DOM heuristic scan chalata hai,
     * target element ko green highlight karta hai, aur Base64 visual snapshot capture karta hai.
     */
    public WebElement findElementWithHealing(By originalLocator, String hint) {
        try {
            return driver.findElement(originalLocator);
        } catch (Exception e) {
            System.out.println("[HEALING] Primary locator failed: " + originalLocator);
            System.out.println("[HEALING] Scanning DOM with semantic hint: " + hint);

            List<WebElement> candidates = driver.findElements(By.xpath("//*[@id or @name or @class or @data-test or @value]"));

            for (WebElement element : candidates) {
                try {
                    String id = element.getAttribute("id");
                    String name = element.getAttribute("name");
                    String dataTest = element.getAttribute("data-test");
                    String value = element.getAttribute("value");
                    String text = element.getText();

                    if ((id != null && id.toLowerCase().contains(hint.toLowerCase())) ||
                        (name != null && name.toLowerCase().contains(hint.toLowerCase())) ||
                        (dataTest != null && dataTest.toLowerCase().contains(hint.toLowerCase())) ||
                        (value != null && value.toLowerCase().contains(hint.toLowerCase())) ||
                        (text != null && text.toLowerCase().contains(hint.toLowerCase()))) {

                        System.out.println("[HEALING] Match confirmed. Highlighting and taking snapshot.");

                        // Visual Green Highlight Draw
                        try {
                            if (driver instanceof JavascriptExecutor) {
                                JavascriptExecutor js = (JavascriptExecutor) driver;
                                js.executeScript("arguments[0].style.border='4px solid #10b981'; arguments[0].style.boxShadow='0 0 12px #10b981';", element);
                                Thread.sleep(400);
                            }

                            // Capture Base64 Snapshot
                            if (driver instanceof TakesScreenshot) {
                                this.healedScreenshotBase64 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
                            }
                        } catch (Exception ignored) {
                        }

                        return element;
                    }
                } catch (Exception ignored) {
                }
            }

            throw new RuntimeException("Heuristic recovery failed to locate element matching hint: " + hint);
        }
    }
}