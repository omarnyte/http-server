Feature: Text Content Negotiation

Scenario: The text file exists 
  When I send a request to the path of a text file
  And that text file exists in the directory
  Then I should see the contents of the file

Scenario: The image file exists 
  When I send a request to the path of an image file
  And that text file exists in the directory
  Then I should see the contents of the image

Scenario: The text file does not exist
  When I send a request to the path of a text file
  And that text file does not exist in the directory
  Then I should get a 404 Not Found response