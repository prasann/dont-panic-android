Feature: Home feature

  Scenario: I can access different component of app
    When I press "Offices"
    Then I see "TW GGN"
    Then I see "6767676"
    Then I see "tw@ggn.com"
    Then I go back
    
    When I press "Gyms"
    Then I see "Gold Gym"
    Then I see "4444444444"
    Then I see "goldgym@ggn.com"
    Then I go back

    When I press "Admins"
    Then I see "Damandeep Kaur"
    Then I see "9999999999"
    Then I see "damandeep.kaur@thoughtworks.com"
    Then I go back

    When I press "Malls"
    Then I see "Ambience"
    Then I see "8888888888"
    Then I see "amb@ggn.com"
    Then I go back

    When I press "Pubs"
    Then I see "Last Chance"
    Then I see "7777777777"
    Then I see "lastchance@ggn.com"
    Then I go back

    When I press "Restaurants"
    Then I see "Pind Baluchi"
    Then I see "6666666666"
    Then I see "pindbaluchi@ggn.com"
    Then I go back