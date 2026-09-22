package com.testbuddy.automation;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SelfHealingDriver {

    private final WebDriver driver;

    public SelfHealingDriver(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Tries default locator; if missing, triggers self-healing resolution.
     */
    public WebElement findElementWithHealing(By originalBy, String tagOrTextHint) {
        try {
            return driver.findElement(originalBy);
        } catch (NoSuchElementException e) {
            System.out.println("[SELF-HEAL WARNING] Original locator failed: " + originalBy);
            System.out.println("[SELF-HEAL ACTION] Initiating autonomous DOM scan for: " + tagOrTextHint);

            WebElement healedElement = healElement(tagOrTextHint);
            if (healedElement != null) {
                highlightElement(healedElement);
                System.out.println("[SELF-HEAL SUCCESS] Healed element located dynamically.");
                return healedElement;
            }
            throw e; // Element genuinely not on page
        }
    }

    private WebElement healElement(String textHint) {
        // Strategy 1: Fuzzy text search via XPath
        List<WebElement> candidates = driver.findElements(
            By.xpath("//*[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '" 
                     + textHint.toLowerCase() + "')]")
        );
        for (WebElement el : candidates) {
            if (el.isDisplayed() && el.isEnabled()) {
                return el;
            }
        }

        // Strategy 2: Search by input placeholders or attributes
        List<WebElement> attributeCandidates = driver.findElements(
            By.xpath("//*[@name='" + textHint + "' or @placeholder='" + textHint + "' or @aria-label='" + textHint + "']")
        );
        for (WebElement el : attributeCandidates) {
            if (el.isDisplayed()) {
                return el;
            }
        }

        return null;
    }

    private void highlightElement(WebElement element) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].setAttribute('style', 'border: 3px solid #10b981; background: #d1fae5;');", element);
        } catch (Exception ignored) {
        }
    }

    public WebDriver getDriver() {
        return driver;
    }
}