
@ui
Feature: Checkout items in the basket

  Scenario: Check item total cost and tax

    Given I am on the home page

    And I login in with the following details
      | userName      | Password     |
      | standard_user | secret_sauce |

    Then I should see the products page

    And I add the following items to the basket
      | Sauce Labs Backpack      |
      | Sauce Labs Fleece Jacket |
      | Sauce Labs Bolt T-Shirt  |
      | Sauce Labs Onesie        |

    And I should see 4 items added to the shopping cart

    And I click on the shopping cart

    And I verify that the QTY count for each selected item should be 1

    And I remove the following items:
      | Sauce Labs Fleece Jacket |
      | Sauce Labs Onesie        |

    And I should see 2 items added to the shopping cart

    And I click on the CHECKOUT button

    And I type "FirstName" for First Name

    And I type "LastName" for Last Name

    And I type "EC1A 9JU" for ZIP/Postal Code

    When I click on the CONTINUE button

    Then Item total will be equal to the total of items on the list

    And a Tax rate of 8 % is applied to the total


  Scenario Outline: Login should be rejected for invalid credentials

    Given I am on the home page

    When I login with username "<username>" and password "<password>"

    Then I should see the login error "<error>"

    And I should remain on the login page

    Examples:
      | username        | password     | error                                                                     |
      | locked_out_user | secret_sauce | Epic sadface: Sorry, this user has been locked out.                        |
      | invalid_user    | invalid_pass | Epic sadface: Username and password do not match any user in this service |
      |                 | secret_sauce | Epic sadface: Username is required                                         |
      | standard_user   |              | Epic sadface: Password is required                                         |


  Scenario: Checkout should validate mandatory information fields

    Given I am on the home page

    And I login in with the following details
      | userName      | Password     |
      | standard_user | secret_sauce |

    And I add the following items to the basket
      | Sauce Labs Backpack |

    And I click on the shopping cart

    And I click on the CHECKOUT button

    When I submit the checkout information

    Then I should see the checkout error "Error: First Name is required"

    And I should remain on the checkout information page

    When I type "FirstName" for First Name

    And I submit the checkout information

    Then I should see the checkout error "Error: Last Name is required"

    And I should remain on the checkout information page

    When I type "LastName" for Last Name

    And I submit the checkout information

    Then I should see the checkout error "Error: Postal Code is required"

    And I should remain on the checkout information page
