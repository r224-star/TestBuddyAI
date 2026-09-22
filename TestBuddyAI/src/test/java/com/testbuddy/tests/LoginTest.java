package com.testbuddy.tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.testbuddy.automation.DriverManager;
import com.testbuddy.automation.LoginPage;

public class LoginTest {

    WebDriver driver;

    @BeforeMethod
    public void setUp() {
        DriverManager.startBrowser();
        driver = DriverManager.getDriver();
    }

    @Test
    public void validLoginTest() {
        driver.get("https://www.saucedemo.com");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        String currentUrl = driver.getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);

        Assert.assertTrue(currentUrl.contains("inventory"), "Login failed - not redirected to inventory page");
    }

    @AfterMethod
    public void tearDown() {
        DriverManager.closeBrowser();
    }
}