Feature: Create a resource

Background: 
  Given a directory /people exists 

Scenario: POST request with JSON
  When a client makes a POST request to /api/people
  And the request contains the application/json message body
  """
  { "aKey": "aValue" }
  """
  Then the server should respond with status code 201 Created
  And the response should contain the header Location

Scenario: POST request with plain text  
  When a client makes a POST request to /api/people
  And the request contains the text/plain message body
  """
  plain text body
  """
  Then the server should respond with status code 201 Created
  And the response should contain the header Location
  
Scenario: POST request with unsupported content type
  When a client makes a POST request to /api/people
  And the request contains the unsupported/type message body 
  """
  this type is not supported
  """
  Then the server should respond with status code 415 Unsupported Media Type
  