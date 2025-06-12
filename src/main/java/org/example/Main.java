package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class Main {
    public static void main(String[] args) {

        WebDriver driver = new ChromeDriver();

        driver.navigate().to("https://www.popmart.com/us");

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

        WebElement acceptButton = driver.findElement(By.xpath("//div[text()='ACCEPT']"));

        acceptButton.click();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        WebElement signInButton = driver.findElement(By.xpath("//div[text()='Sign in / Register']"));

        signInButton.click();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        WebElement typeEmail = driver.findElement(By.xpath("//input[@id='email']"));

        typeEmail.sendKeys("testtest@gmail.com");

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        WebElement continueButton = driver.findElement(By.xpath("//button[text()='CONTINUE']"));

        continueButton.click();

        WebElement typePassword = driver.findElement(By.xpath("//input[@id='password']"));

        typePassword.sendKeys("Testpassword");

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        WebElement continueToCode = driver.findElement(By.xpath("//button[text()='CONTINUE TO SEND CODE']"));

        continueButton.click();


        // TO DO


        // add password adding
        // add browser proxy support
        // add IMAP support
        // Pull info from file
        // Save email + password to file





    }
}