package com.tau.base;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * This class is used to Selenium Grid testing.
 * It will return Chrome/Firefox browser remote driver.
 * Please keep all your web driver exe in the same folder where Selenium
 * stand alone jar file is kept.
 * Command to start Hub - 1st go to the directory where selenium server standalone jar is kept. Then
 * type command = java -jar selenium-server-standalone-3.141.59.jar -role hub
 * Command to start Node - 1st go to the directory where selenium server standalone jar is kept. Then
 * type command = java -jar selenium-server-standalone-3.141.59.jar -role node -hub http://localhost:4444/grid/register
 * You can replace the localhost with IP address of the machine where server is running.
 */

public class TestBaseGrid {
    private static WebDriver driver = null;
    private String nodeURL;

    public WebDriver getDriver() throws MalformedURLException {
        nodeURL = "http://localhost:4444/wd/hub";
//        ChromeOptions chromeOptions = new ChromeOptions();
//        chromeOptions.setCapability("Platform", Platform.WIN8_1);
//        driver = new RemoteWebDriver(new URL(nodeURL), chromeOptions);

        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setCapability("Platform", Platform.WIN8_1);
        driver = new RemoteWebDriver(new URL(nodeURL), firefoxOptions);

        driver.manage().window().maximize();
        return driver;
    }
}
