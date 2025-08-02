package com.yatram_automation.utility;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class BaseClass {
//    public AndroidDriver driver;
    public AppiumDriver driver;

    public void launchApp() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("appium:deviceName", "motorola moto g54 5G");
        capabilities.setCapability("appium:udid", "ZD222HXNJP");
        capabilities.setCapability("appium:platformVersion", "15");
        capabilities.setCapability("appium:automationName", "uiautomator2");
        capabilities.setCapability("appium:appPackage", "com.pavitramcustomerapp");
        capabilities.setCapability("appium:appActivity", "com.pavitramcustomerapp.MainActivity");
//        capabilities.setCapability("app", System.getProperty("user.dir") + "/src/main/resources/Pavitram-Customer-V1.apk");
//        capabilities.setCapability("appium:newCommandTimeout", 60);


        try {
            URL url = new URL("http://127.0.0.1:4723/");
            driver = new AndroidDriver(url, capabilities);
            System.out.println("App launched successfully!");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException("Appium server URL is malformed");
        }
    }

    public void launchFlipkart() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("appium:deviceName", "motorola moto g54 5G");
        capabilities.setCapability("appium:udid", "ZD222HXNJP");
        capabilities.setCapability("appium:platformVersion", "15");
        capabilities.setCapability("appium:automationName", "uiautomator2");
        capabilities.setCapability("appium:appPackage", "com.flipkart.android");
        capabilities.setCapability("appium:appActivity", "com.flipkart.android.SplashActivity");
//        capabilities.setCapability("appium:newCommandTimeout", 60);



        try {
            URL url = new URL("http://127.0.0.1:4723/");
            driver = new AndroidDriver(url, capabilities);
            System.out.println("App launched successfully!");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException("Appium server URL is malformed");
        }
        /*UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName("motorola moto g54 5G");
        options.setUdid("ZD222HXNJP");
        options.setPlatformVersion("15");
        options.setAutomationName("uiautomator2");
        options.setAppPackage("com.flipkart.android");
        options.setAppActivity("com.flipkart.android.activity.FirstLaunchActivity");
        options.setNewCommandTimeout(Duration.ofSeconds(60)); // Java 8 or above

        try {
            URL url = new URL("http://127.0.0.1:4723/");
            driver = new AndroidDriver(url, options);
            System.out.println("App launched successfully!");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException("Appium server URL is malformed");
        }*/
    }

    public void waitForElementForClickable(By by) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(by));
    }

    public void waitForElementPresence(By by) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement toastLikeView = wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public void waitForElementsVisible(By by) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
    }

    public void enterText(By by, String text) {
        driver.findElement(by).sendKeys(text);
    }

    public void tap(By by) {
        driver.findElement(by).click();
    }

    public void tapOnTick() {
        ((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.ENTER));

    }

    public void scrollToElement(AndroidDriver driver, By by) {
        int maxScrolls = 10; // limit scrolls to avoid infinite loops
        boolean found = false;

        for (int i = 0; i < maxScrolls; i++) {
            try {
                WebElement element = driver.findElement(by);
                if (element.isDisplayed()) {
                    System.out.println("Element is visible after scrolling.");
                    found = true;
                    break;
                }
            } catch (Exception ignored) {
            }

            // Scroll down using TouchAction
            Dimension size = driver.manage().window().getSize();
            int startX = size.width / 2;
            int startY = (int) (size.height * 0.7);
            int endY = (int) (size.height * 0.3);

            new TouchAction<>(driver)
                    .press(PointOption.point(startX, startY))
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)))
                    .moveTo(PointOption.point(startX, endY))
                    .release()
                    .perform();
        }

        if (!found) {
            throw new RuntimeException("Element not found after scrolling.");
        }
    }

    public void uploadImage(String mobFilePath, String machineFilePath) {
        try {
            ((AndroidDriver) driver).pushFile(
                    mobFilePath,
                    new File(machineFilePath)
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void takeScreenshot() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String fileName = "ScreenShot_" + timestamp + ".png";
        String destPath = System.getProperty("user.dir") + "/ScreenShots/" + fileName;

        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(srcFile, new File(destPath));
        } catch (IOException e) {
        }
    }

    public void pullToRefresh(AndroidDriver driver) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence pullDown = new Sequence(finger, 1);

        // Adjust based on your device resolution and app UI
        int startX = 500;      // X coordinate (middle of the screen)
        int startY = 500;      // Start near top
        int endY = 1000;       // End lower (drag down)

        pullDown.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
        pullDown.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        pullDown.addAction(finger.createPointerMove(Duration.ofMillis(700), PointerInput.Origin.viewport(), startX, endY));
        pullDown.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(List.of(pullDown));
    }

    public Dimension getDimensions(){
        Dimension size = driver.manage().window().getSize();
        /*int startX = size.getWidth() / 2;
        int startY = (int) (size.getHeight() * 0.2);  // start near top
        int endY = (int) (size.getHeight() * 0.6);    // pull down*/
        return size;
    }

    public void scrollToBottom(){
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);

        swipe.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), 500, 1500));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(800), PointerInput.Origin.viewport(), 500, 300));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Arrays.asList(swipe));

    }

    public String captureToastLikeMessage(String partialText) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        int retryCount = 0;

        while (retryCount < 3) { // Retry up to 3 times
            try {
                WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//*[contains(@text,'" + partialText + "')]")
                ));
                return element.getText();
            } catch (StaleElementReferenceException e) {
                System.out.println("StaleElementReferenceException caught. Retrying... Attempt " + (retryCount + 1));
                retryCount++;
            }
        }

        throw new RuntimeException("Toast message with partial text '" + partialText + "' could not be captured after retries.");
    }

    public void waitForAllElements(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

}
