package com.yatram_automation.pages;

import com.yatram_automation.utility.BaseClass;
import com.yatram_automation.utility.OtpUtils;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginPage extends BaseClass {

    public LoginPage(AppiumDriver driver){
        this.driver=driver;
    }

    public By mobileNumber=By.xpath("//android.widget.EditText[@resource-id=\"text-input-outlined\"]");
    public By getOtp=By.xpath("//android.view.ViewGroup[@content-desc=\"Get OTP\"]");
    public By continueBtn=By.xpath("//android.view.ViewGroup[@content-desc='Continue']");
    public By resendBtn=By.xpath("//android.widget.TextView[@text=\"Resend OTP\"]");
    public By backBtn = By.xpath("//android.view.ViewGroup[@content-desc=\"\"]");
    public By allowNotifications=By.xpath("//android.widget.Button[@resource-id='com.android.permissioncontroller:id/permission_allow_button']");
    public By locAccess=By.xpath("//android.widget.Button[@resource-id=\"com.android.permissioncontroller:id/permission_allow_foreground_only_button\"]");
    public By useCurrentLocation=By.xpath("//android.widget.TextView[@text='Use your current location']");
    public By userProfile=By.xpath("//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.LinearLayout/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/android.widget.ImageView");
    public By loginToast=By.xpath("//*[contains(@text, 'Please')]");


    public void login(String mobNumber) throws InterruptedException {
        waitForElementForClickable(mobileNumber);
        enterText(mobileNumber, mobNumber);
        tap(getOtp);
        waitForElementForClickable(continueBtn);

        List<WebElement> otpFields = driver.findElements(By.className("android.widget.EditText"));

        //For OTP 9999
        /*for (WebElement otpField : otpFields) {
            otpField.sendKeys("9");
        }*/

        //For OTP from sms notification
        String otp = OtpUtils.waitForOtpFromNotification(10); // or readOtpFromSms()
        System.out.println("OTP is : "+otp);
        for (int i = 0; i < otp.length(); i++) {
            otpFields.get(i).sendKeys(Character.toString(otp.charAt(i)));
        }

        tap(continueBtn);
        waitForElementForClickable(allowNotifications);
        tap(allowNotifications);
        waitForElementForClickable(useCurrentLocation);
//        tap(locAccess);
        tap(useCurrentLocation);
        waitForElementForClickable(locAccess);
        tap(locAccess);

    }

    public String waitForOtpFromSmsInbox(int timeoutInSeconds) throws InterruptedException {
        Thread.sleep(10000);
        int waited = 0;
        while (waited < timeoutInSeconds) {
            try {
                Process process = Runtime.getRuntime().exec("adb shell content query --uri content://sms/inbox");
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;

                while ((line = reader.readLine()) != null) {
                    System.out.println("SMS Line: " + line);

                    if (line.toLowerCase().contains("otp") || line.matches(".*\\b\\d{4,6}\\b.*")) {
                        Matcher matcher = Pattern.compile("\\b\\d{4,6}\\b").matcher(line);
                        if (matcher.find()) {
                            String otp = matcher.group();
                            System.out.println("✅ Extracted OTP: " + otp);
                            return otp;
                        }
                    }
                }

                // Wait 1 second before next attempt
                Thread.sleep(1000);
                waited++;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        throw new RuntimeException("❌ OTP not found in SMS within timeout of " + timeoutInSeconds + " seconds");
    }


    public String readOtpFromSms() {
        try {
            Process process = Runtime.getRuntime().exec("adb shell content query --uri content://sms/inbox");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("SMS: " + line);  // Debug output

                // Match only if it contains OTP keyword
                if (line.toLowerCase().contains("otp") || line.matches(".*\\b\\d{4,6}\\b.*")) {
                    Matcher m = Pattern.compile("\\b\\d{4,6}\\b").matcher(line);
                    if (m.find()) {
                        return m.group();  // Return the OTP
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new RuntimeException("OTP not found in SMS inbox");
    }




}
