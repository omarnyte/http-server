Feature: Root Handler 

  Scenario: GET request to root
    When a client makes a GET request to /
    Then the server should respond with status code 200
    And the server should respond with the contents of the directory of where the JAR is running

  Scenario: GET request to any other endpoint
    When a client makes a GET request to /any/other/endpoint
    Then the server should respond with status code 404