package com.ZTop.qa.Ulster.pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SignIn {
    protected WebDriver driver;

    public SignIn(WebDriver driver) {
        this.driver = driver;
    }

    public void loginAndCloseCookies(String login, String password) {
        //close cookie popup
        WebDriverWait waiter = new WebDriverWait(driver, Duration.ofSeconds(5L));
        try {//wait until cookie window appear
            waiter.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class = 'cookie-popup']")));
            //and close it
            driver.findElement(By.xpath("//div[@class = 'cookie-popup']//button[@class='cookie-popup__close']")).click();
        } catch (TimeoutException e) {
            System.out.println("no cookie");
        }

        //login to the site to get offer
        driver.get(driver.getCurrentUrl() + "/login");
        driver.findElement(By.xpath("//input[@id='email']")).sendKeys(login);
        driver.findElement(By.xpath("//input[@id = 'password']")).sendKeys(password);
        driver.findElement(By.xpath("//input[@type = 'submit']")).submit();
    }
}
