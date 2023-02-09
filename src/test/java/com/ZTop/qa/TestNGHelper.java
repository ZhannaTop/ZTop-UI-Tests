package com.ZTop.qa;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;

//choose RemoteWebDriverSetup extension if want to use CrossBrowser testing
//choose WebDriverSetup extension if want to use local testing
public class TestNGHelper extends WebDriverSetup {

    protected static String urlLocation;
    protected static String urlDomain;
    protected String url = urlDomain + "/" + (urlLocation != null ? urlLocation : "");

    @BeforeMethod
    public void appSetup() {
        if (urlLocation != null && urlDomain != null) {
            driver.get(url);
            System.out.println("The method setup process is completed");
        }
    }

    @AfterMethod(alwaysRun = true)
    protected void logStatusOfTestMethod(ITestResult testResult) {
        switch (testResult.getStatus()) {
            case ITestResult.SUCCESS:
                status = "passed";
                break;
            default:
                status = "failed";
        }
    }

    @AfterSuite(alwaysRun = true)
    public void cleanUp() {
        if (driver != null) {
            if (driver.getClass().getSimpleName().equals("RemoteWebDriver")) {
                ((RemoteWebDriver) driver).executeScript("lambda-status=" + status);
            }
            driver.quit();
            System.out.println("All close up activities completed");
        }
    }
}
