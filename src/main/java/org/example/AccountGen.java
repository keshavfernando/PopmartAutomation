package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chromium.ChromiumDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import java.time.Duration;

public class AccountGen {

    public static String imapEmail = ""; //change
    public static String imapPassword = ""; //change


    public static int randomIntGen(int min, int max)
    {
        return ThreadLocalRandom.current().nextInt(min, max);
    }



    public static void flow(String email, String proxyLine) {

        try
        {

            EmailPull client = new EmailPull();
            client.initialize(imapEmail, imapPassword);
            Proxy seleniumProxy = new Proxy();
            seleniumProxy.setHttpProxy(proxyLine).setSslProxy(proxyLine);

            ChromeOptions options = new ChromeOptions();

            options.setProxy(seleniumProxy);
            options.addArguments("--disable-blink-features=AutomationControlled");
            options.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));
            options.setExperimentalOption("useAutomationExtension", false);
            options.addArguments("user-agent=YourRotatingRealisticUserAgent");
            options.addArguments("--start-maximized");
            options.addArguments("--no-sandbox");

            WebDriver driver = new ChromeDriver(options);

            ((JavascriptExecutor) driver).executeScript("Object.defineProperty(navigator, 'webdriver', {get: () => undefined})");

            driver.navigate().to("https://www.whatismyip.com/");
            Thread.sleep(45000);

            driver.navigate().to("https://www.popmart.com/us");

            System.out.println("Got Website for " + email);

            Thread.sleep(60000);
            System.out.println("Sleeping");

            WebElement acceptButton = driver.findElement(By.xpath("//div[text()='ACCEPT']"));

            acceptButton.click();

            Thread.sleep(30000);
            System.out.println("Sleeping");

            WebElement signInButton = driver.findElement(By.xpath("//div[text()='Sign in / Register']"));

            signInButton.click();

            Thread.sleep(20000);
            System.out.println("Sleeping");

            WebElement typeEmail = driver.findElement(By.xpath("//input[@id='email']"));

            typeEmail.sendKeys(email);

            System.out.println("Email entered: " + email);

            Thread.sleep(20000);
            System.out.println("Sleeping");

            WebElement continueButton = driver.findElement(By.xpath("//button[text()='CONTINUE']"));

            continueButton.click();

            Thread.sleep(20000);
            System.out.println("Sleeping");

            WebElement typePassword = driver.findElement(By.xpath("//input[@id='password']"));

            typePassword.sendKeys("Originals123$"); //add password

            System.out.println("Password entered: " + email);

            Thread.sleep(20000);
            System.out.println("Sleeping");

            WebElement continueToCode = driver.findElement(By.xpath("//button[text()='CONTINUE TO SEND CODE']"));

            continueToCode.click();

            Thread.sleep(20000);
            System.out.println("Sleeping");

            WebElement typeCode = driver.findElement(By.xpath("//input[@id='code']"));

            Thread.sleep(60000);
            System.out.println("Sleeping");

            String verificationCode = client.getCode(email);

            typeCode.sendKeys(verificationCode);

            System.out.println("Code entered and pulled: " + email);

            WebElement createAccount = driver.findElement(By.xpath("//button[text()='CREATE MY ACCOUNT']"));

            Thread.sleep(20000);
            System.out.println("Sleeping");

            createAccount.click();

            Thread.sleep(15000);
            System.out.println("Sleeping");

            System.out.println("Account created: " + email);

            client.closeMail();
            driver.close();
            driver.quit();


        } catch (Exception e) {
            e.printStackTrace();
        }






    }
}