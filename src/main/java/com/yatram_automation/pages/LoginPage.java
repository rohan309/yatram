package com.yatram_automation.pages;

import com.yatram_automation.utility.BaseClass;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

public class LoginPage extends BaseClass {

    public LoginPage(AndroidDriver driver){
        this.driver=driver;
    }

    public By mobileNumber=By.xpath("//android.widget.EditText[@resource-id=\"text-input-outlined\"]");
    public By getOtp=By.xpath("//android.view.ViewGroup[@content-desc=\"Get OTP\"]");
    public By continueBtn=By.xpath("//android.view.ViewGroup[@content-desc=\"Continue\"]");
    public By resendBtn=By.xpath("//android.widget.TextView[@text=\"Resend OTP\"]");
    public By backBtn = By.xpath("//android.view.ViewGroup[@content-desc=\"\"]");
    public By locAccess=By.xpath("//android.widget.Button[@resource-id=\"com.android.permissioncontroller:id/permission_allow_foreground_only_button\"]");
    public By userProfile=By.xpath("//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.LinearLayout/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/android.widget.ImageView");


    public void login(String mobNumber) {
        waitForElementForClickable(mobileNumber);
        enterText(mobileNumber, mobNumber);
        tap(getOtp);
        waitForElementForClickable(continueBtn);

        List<WebElement> otpFields = driver.findElements(By.className("android.widget.EditText"));
        for (WebElement otpField : otpFields) {
            otpField.sendKeys("9");
        }

        tap(continueBtn);
        tap(locAccess);
        waitForElementForClickable(userProfile);

    }

}
