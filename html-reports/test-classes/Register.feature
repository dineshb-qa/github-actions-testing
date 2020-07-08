Feature: Register Functionality
  In order to do internet banking
  I should first register to the application

  Scenario: Register Successful
    Given I am on the registration page of the Para bank application
    When I enter all valid user details
    And I Register to the site
    Then I should be taken to Home page