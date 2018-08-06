Feature: Echo
   GET request to /echo should print "Hello world: hh:mm:ss" on the page 

Scenario: GET request to /echo
  When a client makes a GET request to /echo
  Then the server should respond with status code 200 OK 
  And the server should respond with the current time in hh:mm:ss 
 