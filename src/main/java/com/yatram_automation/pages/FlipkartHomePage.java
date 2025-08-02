package com.yatram_automation.pages;

import org.openqa.selenium.By;

public class FlipkartHomePage {
    public By locPermission = By.xpath("//android.widget.Button[@resource-id=\"com.android.permissioncontroller:id/permission_allow_foreground_only_button\"]");
    public By skipBtn = By.xpath("//android.widget.TextView[@resource-id=\"com.flipkart.android:id/custom_back_icon\"]");
    public By grocceryTab = By.xpath("(//android.widget.FrameLayout[@resource-id=\"com.flipkart.android:id/main_content\"])[1]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup[4]");
    public By searchFeild = By.xpath("(//android.widget.FrameLayout[@resource-id=\"com.flipkart.android:id/main_content\"])[1]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup[4]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup[2]");
    public By searchPageFeild=By.className("android.widget.EditText");
    public By notNowBtn=By.xpath("//android.widget.Button[@resource-id=\"com.flipkart.android:id/not_now_button\"]");





}
