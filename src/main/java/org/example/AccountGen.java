package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chromium.ChromiumDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.util.concurrent.ThreadLocalRandom;

import java.time.Duration;

public class AccountGen {

    public static String imapEmail = "";
    public static String imapPassword = "";


    public static int randomIntGen(int min, int max)
    {
        return ThreadLocalRandom.current().nextInt(min, max);
    }


    public static void flow(String email) {

        try
        {

            EmailPull client = new EmailPull();
            client.initialize(imapEmail, imapPassword);

            WebDriver driver = null;

            int num = randomIntGen(0,2);

            if (num == 0)
            {
                driver = new ChromeDriver();
            }
            else
            {
                driver = new EdgeDriver();
            }

            driver.navigate().to("https://www.popmart.com/us");

            System.out.println("Got Website for " + email);

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(randomIntGen(45,61)));

            WebElement acceptButton = driver.findElement(By.xpath("//div[text()='ACCEPT']"));

            acceptButton.click();

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(randomIntGen(10,20)));

            WebElement signInButton = driver.findElement(By.xpath("//div[text()='Sign in / Register']"));

            signInButton.click();

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(randomIntGen(10,20)));

            WebElement typeEmail = driver.findElement(By.xpath("//input[@id='email']"));

            typeEmail.sendKeys(email);

            System.out.println("Email entered: " + email);

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(randomIntGen(10,20)));

            WebElement continueButton = driver.findElement(By.xpath("//button[text()='CONTINUE']"));

            continueButton.click();

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(randomIntGen(10,20)));

            WebElement typePassword = driver.findElement(By.xpath("//input[@id='password']"));

            typePassword.sendKeys("password"); //add password

            System.out.println("Password entered: " + email);

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(randomIntGen(10,20)));

            WebElement continueToCode = driver.findElement(By.xpath("//button[text()='CONTINUE TO SEND CODE']"));

            continueToCode.click();

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(randomIntGen(480,600)));

            WebElement typeCode = driver.findElement(By.xpath("//input[@id='code']"));

            typeCode.sendKeys(client.getCode(email));

            System.out.println("Code entered and pulled: " + email);

            WebElement createAccount = driver.findElement(By.xpath("//button[text()='CREATE MY ACCOUNT']"));

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(randomIntGen(10,20)));

            createAccount.click();

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(randomIntGen(60,81)));

            System.out.println("Account created: " + email);

            client.closeMail();


        } catch (Exception e) {
            e.printStackTrace();
        }






    }
}