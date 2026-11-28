@api
Feature: ReqRes User API

  Scenario: Retrieve all users across multiple pages
    Given I get the default list of users on page 1
    When I get the list of all users within every page
    Then I should see total users count equals the number of user ids

  Scenario: Retrieve an existing user
    When I make a search for user 3
    Then I should see the following user data
      | first_name | email               |
      | Emma       | emma.wong@reqres.in |

  Scenario: Retrieve a non-existing user
    When I make a search for user 55
    Then the response status should be 404

  Scenario Outline: Create users with different details
    When I create a user with following "<name>" "<job>"
    Then response should contain the following data
      | name   | job   | id | createdAt |
      | <name> | <job> |    |           |

    Examples:
      | name  | job     |
      | Peter | Manager |
      | Liza  | Sales   |

  Scenario: Retrieve users with a response delay
    When I wait for the user list with delay 3 seconds on page 1
    Then the response should take at least 3 seconds
    Then I should see that every user has a unique id

  Scenario: Login with valid credentials
    Given I login with the following data
      | Email              | Password   |
      | eve.holt@reqres.in | cityslicka |
    Then the response status should be 200

  Scenario: Login without a password
    Given I login with the following data
      | Email              | Password |
      | eve.holt@reqres.in |          |
    Then the response status should be 400
    And I should see the following response error
      | error           |
      | Missing password |
