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
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;

public class Login{
    private WebDriver driver = null;
    private final TestBase testBase;
//    private final TestBaseGrid testBaseGrid;

    public Login(TestBase testBase){
        this.testBase = testBase;
//        this.testBaseGrid = new TestBaseGrid();
    }

    @Given("I am on the login page of the Para bank application")
    public void i_am_on_the_login_page_of_the_Para_bank_application() throws MalformedURLException {
        driver = testBase.getDriver();
//        driver = testBaseGrid.getDriver();
        driver.get("http://parabank.parasoft.com/parabank/index.htm");
    }

    @When("I enter valid credentials")
    public void i_enter_valid_credentials() {
        driver.findElement(By.name("username")).sendKeys("cucumber-java-111");
        driver.findElement(By.name("password")).sendKeys("!Password*");
        driver.findElement(By.xpath("//input[@value='Log In']")).click();
    }

    @Then("I should be taken to Overview page")
    public void i_should_be_taken_to_Overview_page() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(text(),'Accounts Overview')]")));

        Assert.assertTrue(driver.findElement(By.className("smallText")).getText().contains("Welcome Cucumber Java"));
        driver.findElement(By.linkText("Log Out")).click();

        driver.quit();
    }

    @When("I enter invalid credentials")
    public void i_enter_invalid_credentials() {
        driver.findElement(By.name("username")).sendKeys("invalid");
        driver.findElement(By.name("password")).sendKeys("invalid");
        driver.findElement(By.xpath("//input[@value='Log In']")).click();
    }

    @When("I enter valid {string} and {string}")
    public void i_enter_valid_username_and_password(String username, String password) {
        driver.findElement(By.name("username")).sendKeys(username);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.xpath("//input[@value='Log In']")).click();
    }

    @Then("I should see proper error message")
    public void i_should_see_proper_error_message() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.className("title"), "Error!"));

        String expectedErrorMsg = "The username and password could not be verified.";
        String actualErrorMsg = driver.findElement(By.className("error")).getText();

        Assert.assertEquals(expectedErrorMsg, actualErrorMsg);
        driver.quit();
    }

    //@After
    public void tearDown(){
        driver.quit();
    }
}
