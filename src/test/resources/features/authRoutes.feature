Feature: Protect routes with  authentication

Background:
  Given a directory /protected exists
  And a file with the name protected-file.txt exists in /protected
 
Scenario: Request missing authenticatino header 
  When a client makes a GET request to /protected
  Then the server should respond with status code 401 Unauthorized

Scenario: Request with valid username but invalid password 
When a client makes a GET request to /protected
And the client provides the credentials: username invalid_password
Then the server should respond with status code 401 Unauthorized

Scenario: Request with valid username and valid password 
When a client makes a GET request to /protected
And the client provides the credentials: username password
Then the server should respond with status code 200 OK
