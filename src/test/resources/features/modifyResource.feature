Feature: Modify a resource

Scenario: PUT request to resource that does not exist
  Given a file with the name will-be-created-and-modified.txt does not exist in the root directory
  When a client makes a PUT request to /will-be-created-and-modified.txt
  Then the server should respond with status code 201 Created
  And a file with the name will-be-created-and-modified.txt exists in the root directory