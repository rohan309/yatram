package com.yatram_automation.pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class SearchPage {
    public AppiumDriver driver;

    public SearchPage(AppiumDriver driver) {
        this.driver = driver;
    }

    /*public By horizontalCards = By.xpath(
            "//android.widget.ScrollView/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]" +
                    "/android.view.ViewGroup/android.widget.HorizontalScrollView[2]/android.view.ViewGroup" +
                    "/android.view.ViewGroup/android.view.ViewGroup"
    );*/
    public By horizontalCards = By.xpath("//android.widget.TextView");
    public By grantLocPermission=By.xpath("//android.widget.Button[@resource-id='com.android.permissioncontroller:id/permission_allow_one_time_button']");
    public By skipBtn=By.xpath("//android.widget.TextView[@resource-id='com.flipkart.android:id/custom_back_icon']");
    public By searchField=By.xpath("(//android.widget.FrameLayout[@resource-id='com.flipkart.android:id/main_content'])[1]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup[4]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]");


}
