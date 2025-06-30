package org.example;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.Proxy;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

public class AccountGenNew {


    public Playwright playwright;
    public Browser browser;
    public Page page;

    public static void sleepRandom(int minMillis, int maxMillis)
    {
        int sleepTime = ThreadLocalRandom.current().nextInt(minMillis, maxMillis);
        try
        {
            Thread.sleep(sleepTime);
            System.out.println("Slept for " + sleepTime + "ms");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Sleep interrupted");
        }
    }


    public void flowNew(EmailPull client, String email, String host, String port, String username, String password, String userAgent, String acctPass)
    {

        try
        {
            playwright = Playwright.create();
            browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                    .setHeadless(false)
                    .setArgs(List.of(
                            "--disable-blink-features=AutomationControlled",
                            "--no-sandbox",
                            "--start-maximized"
                    ))
                    .setProxy(new Proxy("http://" + host + ":" + port)
                    .setUsername(username)
                    .setPassword(password)));
            BrowserContext context = browser.newContext(new Browser.NewContextOptions()
                    .setUserAgent(userAgent)
                    .setViewportSize(1920,1080)
                    .setLocale("en-US")
                    .setTimezoneId("America/New_York"));

            page = context.newPage();
            page.addInitScript("Object.defineProperty(navigator, 'webdriver', { get: () => undefined });");
            page.addInitScript("window.chrome = { runtime: {} };");
            page.addInitScript("Object.defineProperty(navigator, 'languages', { get: () => ['en-US', 'en'] });");
            page.addInitScript("Object.defineProperty(navigator, 'plugins', { get: () => [1, 2, 3, 4, 5] });");

            page.navigate("https://popmart.com/us");
            sleepRandom(25000,40000);

            System.out.println("Got website for " + email);


            if (page.locator("xpath=//*[@id=\"__next\"]/div/div/div[1]/div/div[3]").isVisible())
            {
                page.locator("xpath=//*[@id=\"__next\"]/div/div/div[1]/div/div[3]").click();
                System.out.println("Clicked United States");
                sleepRandom(5555,10000);
            }

            page.locator("//div[text()='ACCEPT']").click();
            System.out.println("Clicked ACCEPT");
            sleepRandom(5555,10000);

            page.locator("//div[text()='Sign in / Register']").click();
            System.out.println("Got Account Sign In Page");
            sleepRandom(5555,8888);
            page.locator("//input[@id='email']").type(email);
            System.out.println("Email entered: " + email);
            sleepRandom(5555,8888);
            page.locator("//button[text()='CONTINUE']").click();
            System.out.println("Clicked CONTINUE");
            sleepRandom(5555,8888);
            page.locator("//input[@id='password']").type(acctPass);
            System.out.println("Password entered: " + email);
            sleepRandom(5555,8888);
            page.locator("//button[text()='CONTINUE TO SEND CODE']").click();
            System.out.println("Clicked CONTINUE TO CODE");
            sleepRandom(40000,45000);
            System.out.println("Waiting for verification e‑mail …");
            String verificationCode = client.getCode(email);
            page.locator("//input[@id='code']").type(verificationCode);
            sleepRandom(5555,8888);
            System.out.println("Code entered and pulled: " + email);
            page.locator("//button[text()='CREATE MY ACCOUNT']").click();
            sleepRandom(45000,50000);
            System.out.println("Account created: " + email);
            client.closeMail();
            browser.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
