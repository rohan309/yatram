package com.yatram_automation;

import com.yatram_automation.pages.LoginPage;
import com.yatram_automation.utility.BaseClass;
import com.yatram_automation.utility.PropertyHandler;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class Login extends BaseClass {

    PropertyHandler propertyHandler;
    LoginPage loginPage;
    @BeforeClass
    public void setUp(){
        launchApp();
        propertyHandler=new PropertyHandler();
        loginPage=new LoginPage(driver);
    }

    @AfterClass
    public void tearDown(){
        driver.quit();
    }

    @Test
    public void login() {

        enterText(loginPage.mobileNumber, propertyHandler.getValue("mobileNumber"));
        tap(loginPage.getOtp);
        waitForElementForClickable(loginPage.continueBtn);

        List<WebElement> otpFields = driver.findElements(By.className("android.widget.EditText"));
        for (WebElement otpField : otpFields) {
            otpField.sendKeys("9");
        }

        tap(loginPage.continueBtn);
        tap(loginPage.locAccess);
        waitForElementForClickable(loginPage.userProfile);

        Assert.assertTrue(driver.findElement(loginPage.userProfile).isDisplayed());


    }

    @Test(dataProvider = "invalidMobileNumbers")
    public void loginWithInvalidNumber(String mobileNumber){
        waitForElementForClickable(loginPage.mobileNumber);
        driver.findElement(loginPage.mobileNumber).clear();
        enterText(loginPage.mobileNumber, mobileNumber);
        tap(loginPage.getOtp);
    }

    @DataProvider(name = "invalidMobileNumbers")
    public Object[][] invalidMobileNumbers() {
        return new Object[][]{
                {"12345"},            // Too short
                {"abcdefghij"},       // Alphabets
//                {"999999999999"},     // Too long
                {"12345abcde"},       // Alphanumeric
                {"!@#$%^&*()"},       // Special characters
                {""},                 // Empty string
                {"          "},       // Only spaces
                {"0000000000"},       // Invalid repetitive number
        };
    }

}
