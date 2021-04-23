Feature: VIC Road

  @selenium
  Scenario: Verify the rego fee calculation feature on Vic Roads site
    Given I am on the home page of vic roads site
    Then I should be able to calculate and see the rego fees for various vehicle types
