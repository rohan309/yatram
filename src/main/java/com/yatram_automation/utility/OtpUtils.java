package com.yatram_automation.utility;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.Duration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OtpUtils {

    /**
     * Waits for a new SMS to arrive in the inbox and extracts a 4–6 digit OTP from it.
     *
     * @param timeoutInSeconds max seconds to wait for SMS
     * @return extracted OTP as string (e.g., "9955")
     */

    public static String waitForOtpFromNotification(int timeoutInSeconds) {
        int waited = 0;
        String lastOtp = "";

        while (waited < timeoutInSeconds) {
            try {
                // 🔁 Get current notifications
                Process process = Runtime.getRuntime().exec("adb shell dumpsys notification --noredact");
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

                StringBuilder notificationDump = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    notificationDump.append(line).append("\n");
                }

                String content = notificationDump.toString();
                System.out.println("🔔 Notification Dump Captured");

                // ✅ Extract OTP from pattern like "OTP is 3778"
                Pattern pattern = Pattern.compile("OTP is (\\d{4})");
                Matcher matcher = pattern.matcher(content);

                if (matcher.find()) {
                    String otp = matcher.group(1); // Extracts only the 4 digits
                    if (!otp.equals(lastOtp)) {
                        System.out.println("✅ OTP from Notification: " + otp);
                        return otp;
                    }
                }

                Thread.sleep(1000);
                waited++;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        throw new RuntimeException("❌ OTP not found in notifications within " + timeoutInSeconds + " seconds");
    }

    /*public static void clearAllNotifications() {
        try {
            Process process = Runtime.getRuntime().exec("adb shell cmd notification cancel-all");
            process.waitFor(); // optional: wait for completion
            System.out.println("✅ Cleared all notifications");
        } catch (Exception e) {
            System.out.println("❌ Failed to clear notifications");
            e.printStackTrace();
        }
    }*/

    public static void clearNotifications(AppiumDriver driver) {
        String partialText = "Pavitram";

        try {
            if (driver instanceof AndroidDriver) {
                AndroidDriver androidDriver = (AndroidDriver) driver;

                androidDriver.openNotifications();
                Thread.sleep(2000); // wait for drawer to open

                List<WebElement> notifications = driver.findElements(
                        By.xpath("//android.widget.TextView[contains(@text, '" + partialText + "')]")
                );

                for (WebElement notif : notifications) {
                    int startX = notif.getLocation().getX() + 300;
                    int endX = notif.getLocation().getX() - 300;
                    int y = notif.getLocation().getY();

                    PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
                    Sequence swipe = new Sequence(finger, 1);
                    swipe.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), startX, y));
                    swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
                    swipe.addAction(finger.createPointerMove(Duration.ofMillis(300), PointerInput.Origin.viewport(), endX, y));
                    swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

                    driver.perform(List.of(swipe));
                    System.out.println("✅ Swiped notification with text: " + notif.getText());
                }

                if (notifications.isEmpty()) {
                    System.out.println("ℹ️ No matching notifications found with text: " + partialText);
                }

                // 👇 Close notification panel
                androidDriver.pressKey(new KeyEvent(AndroidKey.BACK));
                System.out.println("✅ Notification panel closed");

            } else {
                System.out.println("❌ Driver is not AndroidDriver");
            }
        } catch (Exception e) {
            System.out.println("⚠️ Error while clearing notifications: " + e.getMessage());
            e.printStackTrace();
        }
    }



}

