Feature: Query Parameter Parsing 

Scenario: GET request to /api/query with single parameter and no reserved characters
  When a client makes a GET request to /api/query?firstKey=firstValue
  Then the server should respond with status code 200 OK
  And the server should respond with a message body of "firstKey : firstValue"