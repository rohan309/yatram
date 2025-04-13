package com.yatram_automation.pages;

import org.openqa.selenium.By;

public class HomePage {
    public By searchPage=By.xpath("//android.view.ViewGroup[@content-desc=\"Search\"]");
    public By searchFeild=By.xpath("//android.widget.EditText[@resource-id=\"text-input-outlined\"]");
    public By searchResult=By.xpath("//android.widget.TextView[@text=\"Yezdi Momo\"]");

}
