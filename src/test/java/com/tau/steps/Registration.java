package com.tau.steps;

import com.tau.base.TestBase;
import com.tau.base.TestBaseGrid;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class Registration {
    private WebDriver driver = null;
    private final TestBase testBase;
    private String username;
//    private final TestBaseGrid testBaseGrid;

    public Registration(){
        this.testBase = new TestBase();
//        this.testBaseGrid = new TestBaseGrid();
    }

    @Given("I am on the registration page of the Para bank application")
    public void i_am_on_the_registration_page_of_the_Para_bank_application() throws MalformedURLException {
        driver = testBase.getDriver();
//        driver = testBaseGrid.getDriver();
        driver.get("http://parabank.parasoft.com/parabank/index.htm");
        driver.findElement(By.linkText("Register")).click();
    }

    @When("I enter all valid user details")
    public void i_enter_all_valid_user_details() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.className("title"), "Signing up is easy!"));

        Random random = new Random();
        int number = random.nextInt(100);
        username = "cucumber-java-" + number;
        System.out.println("***********************"+username);
        //enter all details
        driver.findElement(By.id("customer.firstName")).sendKeys("Cucumber");
        driver.findElement(By.id("customer.lastName")).sendKeys("Java");
        driver.findElement(By.id("customer.address.street")).sendKeys("New York, Street");
        driver.findElement(By.id("customer.address.city")).sendKeys("New York City");
        driver.findElement(By.id("customer.address.state")).sendKeys("New York State");
        driver.findElement(By.id("customer.address.zipCode")).sendKeys("87468");
        driver.findElement(By.id("customer.phoneNumber")).sendKeys("1234567890");
        driver.findElement(By.id("customer.ssn")).sendKeys("4234654654");
        driver.findElement(By.id("customer.username")).sendKeys(username);
        driver.findElement(By.id("customer.password")).sendKeys("!Password*");
        driver.findElement(By.id("repeatedPassword")).sendKeys("!Password*");
    }

    @When("I Register to the site")
    public void i_Register_to_the_site() {
        driver.findElement(By.xpath("//*[@value='Register']")).click();

        //check if username exists error displayed
        try
        {
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("customer.username.errors")));

            Random random = new Random();
            int number = random.nextInt(100);

            driver.findElement(By.id("customer.username")).clear();
            driver.findElement(By.id("customer.username")).sendKeys(username+number);
            driver.findElement(By.id("customer.password")).sendKeys("!Password*");
            driver.findElement(By.id("repeatedPassword")).sendKeys("!Password*");
            driver.findElement(By.xpath("//*[@value='Register']")).click();
        }
        catch (Exception e){}
    }

    @Then("I should be taken to Home page")
    public void i_should_be_taken_to_Home_page() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.className("title"), "Welcome " + username));

        String welcomeMsg = driver.findElement(By.xpath("//*[@id='rightPanel']/p")).getText();
        assertEquals("Your account was created successfully. You are now logged in.", welcomeMsg);

        driver.findElement(By.linkText("Log Out")).click();
        driver.quit();
    }

    //@After
    public void tearDown(){
        driver.quit();
    }
}
