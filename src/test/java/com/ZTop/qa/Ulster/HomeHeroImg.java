package com.ZTop.qa.Ulster;

import com.ZTop.qa.NDM.pageObject.SignIn;
import com.github.romankh3.image.comparison.ImageComparison;
import com.github.romankh3.image.comparison.model.ImageComparisonResult;
import com.github.romankh3.image.comparison.model.ImageComparisonState;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.testng.annotations.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class HomeHeroImg extends BaseClass {
    static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd-HH.mm.ss");
    static DateTimeFormatter dtfFolder = DateTimeFormatter.ofPattern("yyyy.MM.dd");
    static LocalDateTime initialTime = LocalDateTime.now();

    private static final Logger logger = LogManager.getLogger(HomeHeroImg.class);

    @Test(enabled = true)
    public void myAccount(String url) throws IOException {
        driver.get(url);
        new SignIn(driver).loginAndCloseCookies(login, password);
        driver.findElement(By.xpath("//div[@class='header__usercontrols']//a[contains(@href,'my-profile')]")).click();
        driver.findElement(By.xpath("//input[@id = 'first_name']")).sendKeys("ZhTest");
        driver.findElement(By.xpath("//input[@type = 'password']")).sendKeys(password);
        driver.findElement(By.xpath("//input[@type = 'submit']")).submit();
    }

    @Test(enabled = true)
    public void screenShotHomeHero() throws IOException, InterruptedException {
        driver.get(url);
        new SignIn(driver).loginAndCloseCookies(login, password);

        BufferedImage expectedImageHome = new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB); // empty image to prevent NPE during comparison
        BufferedImage expectedImageMatch = new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB); // empty image to prevent NPE during comparison
        //Thread.sleep(60_000); // wait until switch to rdp
        int count = 0; // quantity of images

        while (true) {
            HeroImgResult homePage = checkHero(expectedImageHome, "Home_page", url);
            //Save current image as previous
            expectedImageHome = homePage.getImage();

            HeroImgResult matchCentre = checkHero(expectedImageMatch, "Match_centre", url + "/match/268612");
            //Save current image as previous
            expectedImageMatch = matchCentre.getImage();

            if (homePage.isDefault && count > 10) {
                break;
            }
            count++;
            Thread.sleep(40_000); // screenshot one per minute
        }
    }

    class HeroImgResult {
        BufferedImage image;
        boolean isDefault;

        public HeroImgResult(BufferedImage image, boolean isDefault) {
            this.image = image;
            this.isDefault = isDefault;
        }

        public BufferedImage getImage() {
            return image;
        }

        public boolean isDefault() {
            return isDefault;
        }
    }

    public HeroImgResult checkHero(BufferedImage expectedImg, String page, String url) throws IOException, InterruptedException {
        driver.get(url);
        Thread.sleep(2_000); //2 sec to load elements
        List<WebElement> homeHeroImg = driver.findElements(By.xpath("//div[contains(@class,'generic-hero-image')]"));
        LocalDateTime now = LocalDateTime.now();
        File f;
        if (homeHeroImg.size() == 0) {
            logger.error("Element not found on " + page + " at " + dtf.format(now));
            f = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        } else {
            f = homeHeroImg.get(0).getScreenshotAs(OutputType.FILE); // capture screenshot
        }

        BufferedImage actualImage = ImageIO.read(f); // current image

        //Create ImageComparison object and compare the images
        ImageComparisonResult imageComparisonResult = new ImageComparison(expectedImg, actualImage).compareImages();

        //Check the result
        if (ImageComparisonState.MATCH == imageComparisonResult.getImageComparisonState()) {
            logger.info("ScreenShots are the same on " + page + " at " + dtf.format(now));
            return new HeroImgResult(expectedImg, false);
        } else {
            //Save to file in case of differences
            FileUtils.copyFile(f, new File(dtfFolder.format(initialTime) + "/" + page + "/Hero_" + dtf.format(now) + ".jpg"));
            logger.info("ScreenShot time of " + page + " is " + dtf.format(now));
            try {
                driver.findElement(By.xpath("//button[contains(text(), 'READ MORE')]"));
                logger.info("Default home hero img is here " + dtf.format(now));
                return new HeroImgResult(actualImage, true);
            } catch (NoSuchElementException e) {
            }
            return new HeroImgResult(actualImage, false);
        }
    }

    @Test(enabled = true)
    public void screenShotLineUps() throws IOException, InterruptedException {
        driver.get(url);
        new SignIn(driver).loginAndCloseCookies(login, password);

        while (true) {
            driver.get(url + "/match/268117/lineups");

            WebElement lineupsImg = driver.findElement(By.xpath("//div[contains(@class,'pb-6 md:pb-10')]"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", lineupsImg);
            Thread.sleep(500);
            // capture screenshot with getScreenshotAs() of the dropdown
            File f2 = lineupsImg.getScreenshotAs(OutputType.FILE);
            LocalDateTime now = LocalDateTime.now();
            String fileName2 = "LineUps_" + dtf.format(now) + ".jpg";
            FileUtils.copyFile(f2, new File(fileName2));

            System.out.println("ScreenShot time is " + dtf.format(now));
            Thread.sleep(60000);
        }
    }
}
//        //load images to be compared:
//        BufferedImage expectedImage = ImageComparisonUtil.readImageFromResources("Logo.png"); //design logo
//        BufferedImage actualImage = ImageComparisonUtil.readImageFromResources(siteName);
//
//        // where to save the result (leave null if you want to see the result in the UI)
//        File resultDestination = new File("result" + siteName);
//
//        //Create ImageComparison object and compare the images.
//        ImageComparisonResult imageComparisonResult = new ImageComparison(expectedImage, actualImage, resultDestination).compareImages();
//
//        //Check the result
//        Assert.assertEquals(ImageComparisonState.MATCH, imageComparisonResult.getImageComparisonState());
//
//        //Assert.assertTrue(new File(siteName).exists());




