Feature: Root Handler 

  Scenario: GET request to root
    When a client makes a GET request to /
    Then the server should respond with status code 200 OK 
    And the server should respond with the contents of the root path

  Scenario: GET request to any other endpoint
    When a client makes a GET request to /any/other/endpoint
    Then the server should respond with status code 404 Not Found

  Scenario: HEAD request to root
    When a client makes a HEAD request to /
    Then the server should respond with status code 200 OK 
    And the server should respond with the header Content-Type text/html
    And the server should not respond with a message body
