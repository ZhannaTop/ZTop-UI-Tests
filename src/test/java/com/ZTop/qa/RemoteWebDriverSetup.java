package com.ZTop.qa;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeSuite;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class RemoteWebDriverSetup {
    protected RemoteWebDriver driver = null;

    String username = System.getenv("LABMDA_USERNAME");
    String accesskey = System.getenv("LABMDA_KEY");
    String gridURL = "@hub.lambdatest.com/wd/hub";
    public String status = "failed";

    public static String testName;
    public static boolean hasNetwork;
    public static boolean hasVisual;
    public static boolean hasVideo;
    public static boolean hasConsole;

    @BeforeSuite
    public void setUp() throws Exception {
//        JsonParser parser = new JsonParser();
//        JsonElement config = parser.parseReader(new FileReader("src/test/resources/conf/" + config_file));
//        config.getAsJsonObject().get("user");

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platform", "MacOS Big sur");
        capabilities.setCapability("browserName", "Safari");
        capabilities.setCapability("version", "Latest"); // If this cap isn't specified, it will just get the any available one
        capabilities.setCapability("resolution", "1920x1080");
        //capabilities.setCapability("build", "First Test");
        capabilities.setCapability("name", testName);
        capabilities.setCapability("network", hasNetwork); // To enable network logs
        capabilities.setCapability("visual", hasVisual); // To enable step by step screenshot
        capabilities.setCapability("video", hasVideo); // To enable video recording
        capabilities.setCapability("console", hasConsole); // To capture console logs

        try {
            driver = new RemoteWebDriver(new URL("https://" + username + ":" + accesskey + gridURL), capabilities);
        } catch (MalformedURLException e) {
            System.out.println("Invalid grid URL");
            throw new Exception(e);
        }

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        System.out.println("The setup process is completed");
    }
}
