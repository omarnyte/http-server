Feature: Text Negotiation

Scenario: The text file exists 
  When a client makes a GET request to /sample-text.txt
  Then the server should respond with status code 200 OK 
  And the server should respond with a message body of "This is a sample text file."

Scenario: The text file exists and contains spaces in its name 
  When a client makes a GET request to /file%20with%20spaces.txt
  Then the server should respond with status code 200 OK 
  And the server should respond with a message body of "The title of this text file has spaces."

Scenario: The text file does not exist
  When a client makes a GET request to /does-not-exist.txt
  Then the server should respond with status code 404 Not Found

Scenario: HEAD request to an existing text file
  When a client makes a HEAD request to /sample-text.txt
  Then the server should respond with status code 200 OK 
  And the server should respond with the header Content-Type text/plain
  And the server should not respond with a message body
