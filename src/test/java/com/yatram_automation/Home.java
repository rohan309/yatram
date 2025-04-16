package com.yatram_automation;

import com.github.javafaker.Faker;
import com.yatram_automation.pages.HomePage;
import com.yatram_automation.pages.LoginPage;
import com.yatram_automation.utility.BaseClass;
import com.yatram_automation.utility.PropertyHandler;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Home extends BaseClass {
    HomePage homePage;
    PropertyHandler propertyHandler;

    @BeforeClass
    public void setUp() {
        launchApp();
        LoginPage loginPage = new LoginPage(driver);
        propertyHandler = new PropertyHandler();
        loginPage.login(propertyHandler.getValue("mobileNumber"));
        homePage = new HomePage();

    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void searchResult() throws InterruptedException {
        tap(homePage.searchPage);
        waitForElementForClickable(homePage.searchFeild);
        enterText(homePage.searchFeild, propertyHandler.getValue("searchText"));
        tapOnTick();
        List<WebElement> items = driver.findElements(By.xpath("//android.view.ViewGroup[contains(@content-desc, 'Momo')]"));

        boolean found = false;

        for (WebElement element : items) {
            String desc = element.getAttribute("content-desc");
            if (desc != null && desc.toLowerCase().contains("momo")) {
//                System.out.println("Found content-desc with 'momo': " + desc);
                found = true;
                break;
            }
        }

        Assert.assertTrue(found, "Expected result not found: 'momo' not present in any content-desc.");

        Thread.sleep(3000);

    }

    @Test
    public void logout() throws InterruptedException {
        waitForElementForClickable(homePage.userProfile);
        tap(homePage.userProfile);
        waitForElementForClickable(homePage.logoutOption);
        tap(homePage.logoutOption);
        waitForElementForClickable(homePage.confirmLogout);
        tap(homePage.confirmLogout);

    }

    @Test
    public void addFeedback() throws InterruptedException {
        tap(homePage.userProfile);
        waitForElementForClickable(homePage.contactUsOption);
        tap(homePage.contactUsOption);

        Faker faker = new Faker();
        String name = faker.name().fullName();

        enterText(homePage.feedbackName, name);
        String feedback = faker.lorem().sentence();
        enterText(homePage.feedback, feedback);
        tap(homePage.attachScrrenShot);
        tap(homePage.openSetting);
        waitForElementForClickable(homePage.permission);
        tap(homePage.permission);
        tap(homePage.photoAndVideo);
        waitForElementForClickable(homePage.alwaysAllowAll);
        tap(homePage.alwaysAllowAll);
        tap(homePage.backSettings);
        tap(homePage.backSettings);
        tap(homePage.finalBack);

        String mobFilePath = propertyHandler.getValue("mobFilePath");
        String machineFilePath = propertyHandler.getValue("machineFilePath");

        uploadImage(mobFilePath, machineFilePath);
        tap(homePage.attachScrrenShot);
        waitForElementForClickable(homePage.selectImage);
        tap(homePage.selectImage);
        tap(homePage.submitBtn);
        tap(homePage.backToHome);


    }

    @Test
    public void allTransactions() throws InterruptedException {
        tap(homePage.userProfile);
        Thread.sleep(1000);
        tap(homePage.transactions);
        Thread.sleep(1000);
        takeScreenshot();

        List<Map<String, String>> pendingPayments = new ArrayList<>();
        List<Map<String, String>> completedPayments = new ArrayList<>();

        waitForElementsVisible(homePage.allTransactions);
        List<WebElement> allPayments = driver.findElements(homePage.allTransactions);

        for (WebElement payment : allPayments) {
            try {
                String amount = payment.findElement(By.xpath(".//android.widget.TextView[1]")).getText();
                String type = payment.findElement(By.xpath(".//android.widget.TextView[2]")).getText();
                String txnId = payment.findElement(By.xpath(".//android.widget.TextView[3]")).getText();
                String time = payment.findElement(By.xpath(".//android.widget.TextView[4]")).getText();
                String status = payment.findElement(By.xpath(".//android.widget.TextView[5]")).getText();

                Map<String, String> paymentData = new HashMap<>();
                paymentData.put("Amount", amount);
                paymentData.put("Type", type);
                paymentData.put("Txn ID", txnId);
                paymentData.put("Time", time);
                paymentData.put("Status", status);

                if (status.equals("Pending")) {
                    pendingPayments.add(paymentData);
                } else if (status.equalsIgnoreCase("Completed")) {
                    completedPayments.add(paymentData);
                }

            } catch (Exception e) {
                System.out.println("Skipped one payment block: " + e.getMessage());
            }
        }

        System.out.println("=== Pending Payments ===");
        for (Map<String, String> pay : pendingPayments) {
            System.out.println(pay);
        }

        System.out.println("\n=== Completed Payments ===");
        for (Map<String, String> pay : completedPayments) {
            System.out.println(pay);
        }
    }

}

