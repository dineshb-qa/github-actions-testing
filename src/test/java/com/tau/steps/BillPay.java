package com.tau.steps;

import com.tau.base.TestBase;
import com.tau.base.TestBaseGrid;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;

public class BillPay {
    private WebDriver driver = null;
    private final TestBase testBase;
//    private final TestBaseGrid testBaseGrid;
    private String payeeName = "Tata Sky";
    private String payeeAmount = "50.00";
    private String accNumber;

    public BillPay(){
        this.testBase = new TestBase();
//        this.testBaseGrid = new TestBaseGrid();
    }

    @Given("I logged in as valid user and navigate to bill pay page")
    public void i_logged_in_as_valid_user_and_navigate_to_bill_pay_page() throws MalformedURLException {
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

        driver.findElement(By.linkText("Bill Pay")).click();
    }

    @When("I enter payee and account details")
    public void i_enter_payee_and_account_details() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.className("title"), "Bill Payment Service"));

        //enter payee details
        driver.findElement(By.name("payee.name")).sendKeys(payeeName);
        driver.findElement(By.name("payee.address.street")).sendKeys("Los Angeles, Street 25");
        driver.findElement(By.name("payee.address.city")).sendKeys("Los Angeles");
        driver.findElement(By.name("payee.address.state")).sendKeys("Orlando");
        driver.findElement(By.name("payee.address.zipCode")).sendKeys("87456");
        driver.findElement(By.name("payee.phoneNumber")).sendKeys("7984651");

        Select accounts = new Select(driver.findElement(By.name("fromAccountId")));
        accNumber = accounts.getOptions().get(0).getText();

        driver.findElement(By.name("payee.accountNumber")).sendKeys(accNumber);
        driver.findElement(By.name("verifyAccount")).sendKeys(accNumber);
        driver.findElement(By.name("amount")).sendKeys(payeeAmount);
    }

    @Then("I should be able to send payment")
    public void i_should_be_able_to_send_payment() {
        driver.findElement(By.xpath("//*[@value='Send Payment']")).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//*[@ng-show='showResult']/*[@class='title']"), "Bill Payment Complete"));

        //verify success message
        String expectedSuccessMsg = "Bill Payment to " + payeeName + " in the amount of $" + payeeAmount + " from account " + accNumber + " was successful.";
        String actualSuccessMsg = driver.findElement(By.xpath("//*[@ng-show='showResult']/p")).getText();

        Assert.assertEquals(expectedSuccessMsg, actualSuccessMsg);

        driver.findElement(By.linkText("Log Out")).click();
        driver.quit();
    }
}
