package com.yatram_automation.utility;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class BaseClass {
    public AndroidDriver driver;

    public void launchApp() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("appium:deviceName", "motorola moto g54 5G");
        capabilities.setCapability("appium:udid", "ZD222HXNJP");
        capabilities.setCapability("appium:platformVersion", "15");
        capabilities.setCapability("appium:automationName", "uiautomator2");
        capabilities.setCapability("appium:appPackage", "com.pavitramcustomerapp");
        capabilities.setCapability("appium:appActivity", "com.pavitramcustomerapp.MainActivity");
//        capabilities.setCapability("app", System.getProperty("user.dir") + "/src/main/resources/Pavitram-Customer-V1.apk");
        capabilities.setCapability("appium:newCommandTimeout", 60);


        try {
            URL url = new URL("http://127.0.0.1:4723/");
            driver = new AndroidDriver(url, capabilities);
            System.out.println("App launched successfully!");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException("Appium server URL is malformed");
        }
    }

    public void waitForElementForClickable(By by) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(by));
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

}
