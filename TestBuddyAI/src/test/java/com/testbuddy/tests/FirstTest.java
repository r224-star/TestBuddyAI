package com.testbuddy.tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.testbuddy.automation.DriverManager;

public class FirstTest {

    WebDriver driver;

    @BeforeMethod
    public void setUp() {

        DriverManager.startBrowser();

        driver = DriverManager.getDriver();
    }

    @Test
    public void openGoogleTest() {

        driver.get("https://www.google.com");

        System.out.println("Page Title: " + driver.getTitle());
    }

    @AfterMethod
    public void tearDown() {

        DriverManager.closeBrowser();
    }
}