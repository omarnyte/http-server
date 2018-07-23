Feature: Text Content Negotiation

Scenario: The text file exists 
  Given the server is running
  When I send a request to the path of a text file
  And that text file exists in the directory
  Then I should see the contents of the file

Scenario: The text file does not exist
  Given the server is running
  When I send a request to the path of a text file
  And that text file does not exist in the directory
  Then I should get a 404 Not Found response