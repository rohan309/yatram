package com.yatram_automation;

import com.yatram_automation.pages.FlipkartHomePage;
import com.yatram_automation.utility.BaseClass;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.*;

import static java.time.Duration.ofMillis;

public class FlipkartTest extends BaseClass {
    FlipkartHomePage flipkartHomePage;

    @BeforeClass
    public void beforeClass()  {
        launchFlipkart();
        flipkartHomePage=new FlipkartHomePage();
    }

    @AfterClass
    public void tearDown(){
        driver.quit();
    }

    @Test
    public void test() throws InterruptedException {

        waitForElementForClickable(flipkartHomePage.locPermission);
        tap(flipkartHomePage.locPermission);
        waitForElementForClickable(flipkartHomePage.skipBtn);
        tap(flipkartHomePage.skipBtn);
        waitForElementForClickable(flipkartHomePage.searchFeild);
        tap(flipkartHomePage.searchFeild);
        waitForElementForClickable(flipkartHomePage.searchPageFeild);
        tap(flipkartHomePage.searchPageFeild);
        enterText(flipkartHomePage.searchPageFeild,"Redmi Mobile");
        ((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.ENTER));

//        driver.pressKey(new KeyEvent(AndroidKey.ENTER));
        waitForElementForClickable(flipkartHomePage.notNowBtn);
        tap(flipkartHomePage.notNowBtn);

        /*List<WebElement> allProducts = driver.findElements(
                By.className("android.widget.TextView")
        );
        System.out.println(allProducts);
        System.out.println("Size of list is "+allProducts.size());
        List<String> list=new ArrayList<>();
        allProducts.forEach(item->{
            list.add(item.getText());
        });
        System.out.println("Item list size : "+list.size());

        list.forEach(item->{
            System.out.println("Item : "+item);
        });*/

        List<WebElement> allMobileNames = driver.findElements(
                By.xpath("//android.widget.TextView[contains(@text, 'REDMI')]")
        );

        scrollToBottom();
        boolean tag=true;
        int cnt=0;
        while (cnt<=3){
            scrollToBottom();
            allMobileNames=  driver.findElements(
                    By.xpath("//android.widget.TextView[contains(@text, 'REDMI')]")
            );
            cnt++;
        }
        System.out.println(allMobileNames.size());
        System.out.println("Mobile names found : ");
        List<String> listOfMobiles=new ArrayList<>();
        for (WebElement mobile : allMobileNames) {
//            System.out.println(mobile.getText());
            listOfMobiles.add(mobile.getText());
        }
        System.out.println(listOfMobiles);
        listOfMobiles.forEach(mob->{
            List<String> mobileName = Collections.singletonList(mob);
            Assert.assertTrue(mobileName.contains("REDMI"));
        });

        Thread.sleep(3000);
    }



}
