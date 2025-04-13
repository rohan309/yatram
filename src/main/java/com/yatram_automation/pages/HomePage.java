package com.yatram_automation.pages;

import org.openqa.selenium.By;

public class HomePage {
    public By searchPage=By.xpath("//android.view.ViewGroup[@content-desc=\"Search\"]");
    public By searchFeild=By.xpath("//android.widget.EditText[@resource-id=\"text-input-outlined\"]");
    public By searchResult=By.xpath("//android.widget.TextView[@text=\"Yezdi Momo\"]");
    public By userProfile=By.xpath("//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.LinearLayout/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/android.widget.ImageView");
    public By logoutOption=By.xpath("//android.widget.TextView[@text='Logout']");
    public By confirmLogout=By.xpath("//android.view.ViewGroup[@content-desc=\"Logout\"]");

    public By userProfOptions=By.xpath("//android.view.ViewGroup[@content-desc]");

}
