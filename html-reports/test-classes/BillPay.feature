Feature: Bill Pay Functionality

  Scenario: Bill Pay
    Given I logged in as valid user and navigate to bill pay page
    When I enter payee and account details
    Then I should be able to send payment