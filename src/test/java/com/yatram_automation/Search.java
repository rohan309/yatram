package com.yatram_automation;

import com.yatram_automation.pages.SearchPage;
import com.yatram_automation.utility.BaseClass;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class Search extends BaseClass {
    SearchPage searchPage;
    @BeforeClass
    public void beforeClass() {
        launchFlipkart();
        searchPage=new SearchPage(driver);
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }

    @Test
    public void allCategories() throws InterruptedException {
        waitForElementForClickable(searchPage.grantLocPermission);
        tap(searchPage.grantLocPermission);
        waitForElementForClickable(searchPage.skipBtn);
        tap(searchPage.skipBtn);
        waitForElementPresence(searchPage.searchField);
        System.out.println("Successfully landed on dashboard");

        waitForAllElements(searchPage.horizontalCards);
        List<WebElement> listOfCategories = driver.findElements(searchPage.horizontalCards);
        System.out.println(listOfCategories.size());

        try {
            listOfCategories.forEach(text -> {
                try {
                    System.out.println(text.getText());
                } catch (StaleElementReferenceException e) {
                    // Ignoring stale element in individual loop
                }
            });
        } catch (StaleElementReferenceException e) {
            listOfCategories = driver.findElements(searchPage.horizontalCards);
            for (WebElement text : listOfCategories) {
                System.out.println(text.getText());
            }
        }
    }

}
