Feature: Echo
   GET request to /echo should print "Hello world: hh:mm:ss" on the page 

Scenario: GET request to /echo
  When a client makes a GET request to /echo
  Then the server should respond with status code 200 OK 
  And the server should respond with the current time in hh:mm:ss 
 
  Scenario: HEAD request to /echo
    When a client makes a HEAD request to /echo
    Then the server should respond with status code 200 OK 
    And the server should respond with the header Content-Type text/plain
    And the server should not respond with a message body
