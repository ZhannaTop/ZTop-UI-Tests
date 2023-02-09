package com.ZTop.qa;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeSuite;

import java.util.concurrent.TimeUnit;

public class WebDriverSetup {

    protected WebDriver driver;
    public String status = "false";
    public static String testName;
    public static boolean hasNetwork;
    public static boolean hasVisual;
    public static boolean hasVideo;
    public static boolean hasConsole;

    @BeforeSuite
    public void setUp() {
        System.setProperty(
                "webdriver.chrome.driver",
                "c:\\Selenium\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        System.out.println("The setup process is completed");
    }


}
