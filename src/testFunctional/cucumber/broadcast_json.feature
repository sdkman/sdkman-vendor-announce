Feature: Broadcast JSON

  Scenario: Identify the latest Broadcast Message
    Given the message "Groovy 2.3.0!" on the date "May 12, 2014" with id "12345"
    And the message "Groovy 2.3.0-rc-1." on the date "May 11, 2014" with id "12344"
    When the identifier of the latest message is requested with "Accept" header "application/json"
    Then the json identifier is "12345"
    And an "OK" status is returned
    And the content type is "application/json"

  Scenario: Broadcast an existing Message by Identifier
    Given the message "Groovy 2.3.0!" on the date "May 12, 2014" with id "12345"
    When a message is requested by identifier "12345" with "Accept" header "application/json"
    Then the content type is "application/json"
    And the json message is "Groovy 2.3.0!"
    And an "OK" status is returned

  Scenario: Broadcast a Message by invalid Identifier
    When a message is requested by identifier "999999999" with "Accept" header "application/json"
    When the content type is "application/json"
    And a "Not Found" message is received
    And a "NOT_FOUND" status is returned

  Scenario: Broadcast a Message
    Given the message "Groovy 2.3.0!" on the date "May 12, 2014" with id "12345"
    When the latest message is requested with "Accept" header "application/json"
    Then the content type is "application/json"
    And has a single result
    And the first json message is "Groovy 2.3.0!"
    And an "OK" status is returned

  Scenario: Broadcast the latest 3 Messages
    Given the message "Groovy 2.3.0 final has been released!" on the date "May 12, 2014"
    And the message "Groovy 2.3.0-rc-1 has been released." on the date "May 11, 2014"
    And the message "Groovy 2.3.0-beta-4 has been released." on the date "May 10, 2014"
    And the message "Groovy 2.3.0-beta-3 has been released." on the date "May 9, 2014"
    When the latest "3" messages are requested with "Accept" header "application/json"
    Then the content type is "application/json"
    And the "first" json message is "Groovy 2.3.0 final has been released!"
    And the "second" json message is "Groovy 2.3.0-rc-1 has been released."
    And the "third" json message is "Groovy 2.3.0-beta-4 has been released."
    And the json message "Groovy 2.3.0-beta-3 has been released." has not been received
    And an "OK" status is returned
