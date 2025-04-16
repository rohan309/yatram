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
    public By contactUsOption=By.xpath("//android.view.ViewGroup[contains(@content-desc, \"Contact Us\")]\n");
    public By feedbackName=By.xpath("//android.widget.EditText[@resource-id=\"text-input-outlined\" and @text=\"Enter your name\"]");
    public By feedback=By.xpath("//android.widget.EditText[@resource-id=\"text-input-outlined\" and @text=\"Write your feedback\"]");
    public By attachScrrenShot=By.xpath("//android.widget.TextView[@text=\"Tap to select an image\"]");
    public By submitBtn=By.xpath("//android.view.ViewGroup[@content-desc=\"Submit Feedback\"]");
    public By backToHome=By.xpath("//android.widget.ImageButton[@content-desc=\"Navigate up\"]");
    public By openSetting=By.xpath("//android.widget.Button[@resource-id=\"android:id/button1\"]");
    public By permission=By.xpath("//android.widget.ScrollView/android.view.View[6]");
    public By photoAndVideo=By.xpath("//android.widget.TextView[@resource-id=\"android:id/title\" and @text=\"Photos and videos\"]");
    public By alwaysAllowAll=By.xpath("//android.widget.RadioButton[@resource-id=\"com.android.permissioncontroller:id/allow_radio_button\"]");
    public By backSettings=By.xpath("//android.widget.ImageButton[@content-desc=\"Navigate up\"]");
    public By finalBack=By.xpath("//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View/android.view.View/android.view.View[1]/android.widget.Button");
    public By album=By.xpath("//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View/android.view.View[3]/android.widget.Button");
    public By testingAlbum=By.xpath("//android.widget.TextView[@text=\"TestingImages\"]");
    public By selectImage= By.xpath("//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View/android.view.View[5]/android.view.View[1]/android.view.View[2]/android.view.View");






}
