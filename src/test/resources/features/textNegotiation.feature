Feature: Text Negotiation

Scenario: The text file exists 
  When a client makes a GET request to /sample-text.txt
  Then the server should respond with status code 200 OK 
  And the server should respond with a message body of "This is a sample text file."

Scenario: The text file does not exist
  When a client makes a GET request to /does-not-exist.txt
  Then the server should respond with status code 404 Not Found
