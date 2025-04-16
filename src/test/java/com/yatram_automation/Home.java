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

import java.util.List;

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

}

