package com.yatram_automation.utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyHandler {

    FileInputStream inputStream;
    Properties properties;

    public PropertyHandler() {
        String file = System.getProperty("user.dir") + "/src/main/resources/config.properties";
        try {
            inputStream = new FileInputStream(file);
            properties = new Properties();
            properties.load(inputStream);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public String getValue(String key) {
        return properties.getProperty(key);
    }
}
