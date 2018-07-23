Feature: Echo
   GET request to /echo should print "Hello world: hh:mm:ss" on the page 

Scenario: title
 Given that the server is running,
  When a client makes a GET request to /echo
  Then the server should respond with "Hello world: [current time in hh:mm:ss]" 
 