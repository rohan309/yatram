package com.yatram_automation;

import com.yatram_automation.pages.LoginPage;
import com.yatram_automation.utility.BaseClass;
import com.yatram_automation.utility.PropertyHandler;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.Duration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login extends BaseClass {

    PropertyHandler propertyHandler;
    LoginPage loginPage;
    @BeforeClass
    public void setUp(){
        launchApp();
//        System.out.println(driver != null ? "driver is not null" : "driver is null");
        propertyHandler=new PropertyHandler();
        loginPage=new LoginPage(driver);
    }

    @AfterClass
    public void tearDown(){
        if (driver != null) {
            driver.quit();
        }

    }

    @BeforeMethod
    public void beforeMethod(){
//        launchApp();
    }

    @AfterMethod
    public void afterMethod(){
//        driver.quit();
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
    public void loginWithInvalidNumber(String mobileNumber) throws InterruptedException {
        waitForElementForClickable(loginPage.mobileNumber);
        driver.findElement(loginPage.mobileNumber).clear();
        enterText(loginPage.mobileNumber, mobileNumber);
        tap(loginPage.getOtp);
//        waitForElementPresence(loginPage.loginToast);
        /*WebElement toast= driver.findElement(loginPage.loginToast);

        String toastMessage = toast.getText();
        System.out.println("Toast Message: " + toastMessage);*/
        /*driver.findElement(loginPage.mobileNumber).clear();
        Thread.sleep(2000);*/
        captureToastLikeMessage("Please");

    }

    @DataProvider(name = "invalidMobileNumbers")
    public Object[][] invalidMobileNumbers() {
        return new Object[][]{
                {"12345"},            // Too short
                {"abcdefghij"},       // Alphabets
//                {"999999999999"},     // Too long
               /* {"12345abcde"},       // Alphanumeric
                {"!@#$%^&*()"},       // Special characters
                {""},                 // Empty string
                {"          "},       // Only spaces
                {"0000000000"},*/       // Invalid repetitive number
        };
    }

    @Test
    public void loginWithValid() throws InterruptedException {
        waitForElementForClickable(loginPage.mobileNumber);
        enterText(loginPage.mobileNumber,"9890732956");
        tap(loginPage.continueBtn);
        enterOtpTest();
        Thread.sleep(3000);
    }

    public static String waitForOtpFromNotification() {
        String otp = "";
        int waited = 0;
        int timeoutInSeconds = 20;

        try {
            while (waited < timeoutInSeconds) {
                // Run ADB command to dump notification content
                Process process = Runtime.getRuntime().exec("adb shell dumpsys notification --noredact");
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                StringBuilder notificationContent = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    // Optionally filter SMS-related notifications
                    if (line.toLowerCase().contains("sms") || line.toLowerCase().contains("otp")) {
                        notificationContent.append(line).append("\n");
                    }
                }

                // Regex to find a 4-digit OTP
                Pattern otpPattern = Pattern.compile("\\b\\d{4}\\b");
                Matcher matcher = otpPattern.matcher(notificationContent.toString());

                if (matcher.find()) {
                    otp = matcher.group(0);
                    System.out.println("OTP received: " + otp);
                    return otp;
                }

                // Wait 3 seconds before retrying
                Thread.sleep(3000);
                waited += 3;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("OTP not received within 20 seconds.");
        return otp;
    }

    public void enterOtpTest() {
        String otp = waitForOtpFromNotification();

        if (!otp.isEmpty()) {
            driver.findElement(By.xpath("//android.widget.EditText[1]")).sendKeys(String.valueOf(otp.charAt(0)));
            driver.findElement(By.xpath("//android.widget.EditText[2]")).sendKeys(String.valueOf(otp.charAt(0)));
            driver.findElement(By.xpath("//android.widget.EditText[3]")).sendKeys(String.valueOf(otp.charAt(0)));
            driver.findElement(By.xpath("//android.widget.EditText[4]")).sendKeys(String.valueOf(otp.charAt(0)));
        } else {
            Assert.fail("Failed to receive OTP.");
        }
    }



}
