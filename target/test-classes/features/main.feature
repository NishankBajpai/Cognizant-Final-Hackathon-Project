Feature: Booking.com website navigation
  Scenario: Get all the info of a cruiseship from a cruiseline
    Given the cruise webpage is opened
    When the user picks a cruise line
    And the user picks a cruise ship
    Then the user collects the information of the ship

  Scenario: Book Holiday Home in Nairobi
    Given the home webpage is opened
    When the user searches for holiday home
    And the user selects the filters
    Then the user collects the data of the homes