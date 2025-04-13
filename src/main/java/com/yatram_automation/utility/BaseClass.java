package com.yatram_automation.utility;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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

    public void waitForElementForClickable(By by){
        WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(by));
    }

    public void enterText(By by,String text){
        driver.findElement(by).sendKeys(text);
    }

    public void tap(By by){
        driver.findElement(by).click();
    }

    public void tapOnTick(){
        ((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.ENTER));

    }
}
