Feature: Delete a resource

Scenario: DELETE request to an existing resource 
  Given a file with the name will-be-deleted.txt exists in the root directory
  When a client makes a DELETE request to /will-be-deleted.txt
  Then the server should respond with status code 204 No Content 
  
Scenario: DELETE request to a resource that has been deleted 
  When a client makes a DELETE request to /will-be-deleted.txt
  Then the server should respond with status code 404 Not Found 
  
Scenario: DELETE request to a resource that does not exist
  Given a file with the name does-not-exist.txt does not exist in the root directory
  When a client makes a DELETE request to /does-not-exist.txt
  Then the server should respond with status code 404 Not Found 
  