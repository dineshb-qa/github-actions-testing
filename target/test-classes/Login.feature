Feature: Login Functionality
  In order to do internet banking
  As a valid Para Bank customer
  I want to login successfully

  Scenario: Login Successful
    Given I am on the login page of the Para bank application
    When I enter valid credentials
    Then I should be taken to Overview page

  Scenario: Login Unsuccessful
    Given I am on the login page of the Para bank application
    When I enter invalid credentials
    Then I should see proper error message