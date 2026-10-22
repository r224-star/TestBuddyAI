package com.testbuddy.automation;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DriverManager {
    private static WebDriver driver;

    public static void startBrowser() {
        if (driver == null) {
            driver = new ChromeDriver();
            driver.manage().window().maximize();
        }
    }

    public static WebDriver getDriver() {
        if (driver == null) {
            startBrowser();
        }
        return driver;
    }

    public static void closeBrowser() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}