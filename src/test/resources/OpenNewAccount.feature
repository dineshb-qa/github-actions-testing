Feature: Open New Account Functionality
  As a valid Para Bank customer
  I should be able to open new account

  Scenario: Open New Account
    Given I logged in as valid user
    And I navigate to open new account page
    When I enter account type and existing account details
    Then I should be able to open new account
    And After selecting an account number account details should be displayed
