package com.tau.steps;

import com.tau.base.TestBase;
import com.tau.base.TestBaseGrid;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.junit.Assert;

import java.net.MalformedURLException;

public class OpenNewAccount {
    private WebDriver driver = null;
    private final TestBase testBase;
//private final TestBaseGrid testBaseGrid;
    private String accNumber;

    public OpenNewAccount(){
        this.testBase = new TestBase();
//        this.testBaseGrid = new TestBaseGrid();
    }

    @Given("I logged in as valid user")
    public void i_logged_in_as_valid_user() throws MalformedURLException {
        driver = testBase.getDriver();
//        driver = testBaseGrid.getDriver();
        driver.get("http://parabank.parasoft.com/parabank/index.htm");

        String username = "cucumber-java-111";

        driver.findElement(By.name("username")).sendKeys(username);
        driver.findElement(By.name("password")).sendKeys("!Password*");
        driver.findElement(By.xpath("//input[@value='Log In']")).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(text(),'Accounts Overview')]")));
        Assert.assertTrue(driver.findElement(By.className("smallText")).getText().contains("Welcome Cucumber Java"));
    }

    @Given("I navigate to open new account page")
    public void i_navigate_to_open_new_account_page() {
        driver.findElement(By.linkText("Open New Account")).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.className("title"), "Open New Account"));
    }

    @When("I enter account type and existing account details")
    public void i_enter_account_type_and_existing_account_details() throws InterruptedException {
        Select accType = new Select(driver.findElement(By.id("type")));
        accType.selectByVisibleText("SAVINGS");

        Thread.sleep(2000);
        Select existingAccList = new Select(driver.findElement(By.id("fromAccountId")));
        existingAccList.selectByIndex(0);
    }

    @Then("I should be able to open new account")
    public void i_should_be_able_to_open_new_account() {
        WebDriverWait wait = new WebDriverWait(driver, 10);

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@value='Open New Account']")));
        driver.findElement(By.xpath("//*[@value='Open New Account']")).click();

        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.className("title"), "Account Opened!"));

        String expectedCongoMsg = "Congratulations, your account is now open.";
        String actualCongoMsg = driver.findElement(By.xpath("//*[@class='ng-scope']/p")).getText();

        Assert.assertEquals(expectedCongoMsg, actualCongoMsg);

        accNumber = driver.findElement(By.id("newAccountId")).getText();
        System.out.println("New account number is : " + accNumber);
    }

    @Then("After selecting an account number account details should be displayed")
    public void after_selecting_an_account_number_account_details_should_be_displayed() {
        driver.findElement(By.id("newAccountId")).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.className("title"), "Account Details"));
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("accountId"), accNumber));

        //verify account details
        Assert.assertEquals(accNumber, driver.findElement(By.id("accountId")).getText());
        Assert.assertEquals("SAVINGS", driver.findElement(By.id("accountType")).getText());
        Assert.assertEquals("$100.00", driver.findElement(By.id("balance")).getText());
        Assert.assertEquals("$100.00", driver.findElement(By.id("availableBalance")).getText());

        driver.findElement(By.linkText("Log Out")).click();
        driver.quit();
    }
}
