Feature: Update a resource with PATCH request 

Background:
  Given a file with the name will-be-patched.json exists in /
  And the file contains the content 
  """
  {
    "aKey": "aValue"
  }
  """

Scenario: Unsupported PATCH document
  When a client makes a PATCH request to /will-be-patched.json
  And the request contains the text/plain message body 
  """
  plain text patch
  """
  Then the server should respond with status code 415 Unsupported Media Type
  And the server should respond with the header Accept-Patch application/json-patch+json

Scenario: Resource not found 
  Given a file with the name does-not-exist.json does not exist in /
  When a client makes a PATCH request to /does-not-exist.json
  And the request contains the application/json message body 
  """
  { "anotherKey": "anotherValue" }
  """
  Then the server should respond with status code 404 Not Found

Scenario: PATCH request to JSON resource 
  When a client makes a PATCH request to /will-be-patched.json
  And the request contains the application/json-patch+json message body 
    """
    [
      { "op": "add", "path": "/addedKey", "value": "addedValue" },
      { "op": "remove", "path": "/aKey" }
    ]
    """
  Then the server should respond with status code 200 OK 
  And the server should respond with a message body of
  """
  {"addedKey":"addedValue"}
  """