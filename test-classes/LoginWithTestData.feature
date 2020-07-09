Feature: Login Functionality With Test Data

  Scenario Outline: Login With Test Data
    Given I am on the login page of the Para bank application
    When I enter valid "<username>" and "<password>"
    Then I should be taken to Overview page

    Examples:
    |username|password|
    |cucumber-java-111|!Password*|
    |cucumber-java-222|!Password*|
    |cucumber-java-333|!Password*|