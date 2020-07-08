package com.tau.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestBase {
    private static WebDriver driver = null;

    public WebDriver getDriver(){
        System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/win/chromedriver.exe");
        driver = new ChromeDriver();
        return driver;
    }
}
