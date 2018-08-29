Feature: Update a resource with PATCH request 

Background:
Given a file with the name will-be-patched exists in /

Scenario: Unsupported PATCH document
  When a client makes a PATCH request to /will-be-patched.json
  And the request contains the text/plain message body "plain text patch"
  Then the server should respond with status code 415 Unsupported Media Type
  And the server should respond with the header Accept-Patch application/json-patch+json

Scenario: Resource not found 
  Given a file with the name does-not-exist.json does not exist in /
  When a client makes a PATCH request to /does-not-exist.json
  And the request contains the application/json message body 
  Then the server should respond with status code 404 Not Found
