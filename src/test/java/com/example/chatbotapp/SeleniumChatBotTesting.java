package com.example.chatbotapp;

import java.time.Duration;
import java.util.stream.Stream;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SeleniumChatBotTesting {
    private WebDriver driver;

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.get("http://localhost:8080/api/chatbot/page"); // Replace with your local Spring Boot app URL
        driver.manage().window().minimize();
    }

    public WebElement waitForElementVisibility(WebDriver driver, By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    @DataProvider(name = "chatData")
    public Object[][] chatData() {
        return new Object[][] {
            {"hello", "Hello! How can I assist you today?"},
            {"hi", "Hi there! How can I help you?"},
            {"how are you?", "I'm just a bot, but thanks for asking!"},
            {"what is your name?", "I am a customer service bot."},
            {"what do you do?", "I assist you with your queries."},
            {"help", "Sure! What do you need help with?"},
            {"HELLO", "Hello! How can I assist you today?"},            
            {"@#$%^&*", "Sorry, I don't understand that."},
            {"a".repeat(10), "Sorry, I don't understand that."},
            {null, "Sorry, I don't understand that."}
        };
    }

    @Test(dataProvider = "chatData")
    public void testChatbotResponses(String input, String expectedResponse) {
        WebElement inputField = waitForElementVisibility(driver, By.id("userInput"), 20);
        WebElement sendButton = waitForElementVisibility(driver, By.id("sendButton"), 20);

        inputField.clear();
        if (input != null) {
            inputField.sendKeys(input);
        }
        sendButton.click();

        handleAlertIfPresent();

        // Increase wait time for the response element
        WebElement responseElement = waitForElementVisibility(driver, By.xpath
        		("//div[@id='chatLog']/p[@class='bot'][last()]"), 15);
        String fullResponse = responseElement.getText();
        String actualResponse = fullResponse.replace("Bot:", "").trim();

        // Log the actual response for debugging
        System.out.println("Input: " + input + " | Expected: " + expectedResponse + " | Actual: " + actualResponse);

        Assert.assertEquals(actualResponse, expectedResponse);
    }

    public void handleAlertIfPresent() {
        try {
            Alert alert = driver.switchTo().alert();
            alert.accept(); // or alert.dismiss();
        } catch (NoAlertPresentException e) {
            // No alert present, continue with the test
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}