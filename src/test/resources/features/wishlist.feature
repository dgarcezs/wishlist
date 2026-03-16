Feature: Wishlist management

  Scenario: Add two products to the wishlist
    Given the wishlist is empty for the customer "1"
    When the customer "1" adds a product "100" to the wishlist
    And the customer "1" adds a product "101" to the wishlist
    Then the wishlist of a customer "1" should contain a product "100"
    And the wishlist of a customer "1" should contain a product "101"


  Scenario: Remove a product from the wishlist
    Given the wishlist is empty for the customer "1"
    When the customer "1" adds a product "100" to the wishlist
    Then the wishlist of a customer "1" should contain a product "100"
    When the customer "1" removes a product "100" from the wishlist
    Then the wishlist of a customer "1" should not contain a product "100"

