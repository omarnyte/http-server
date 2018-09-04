Feature: Modify a resource

Scenario: PUT request to resource that does not exist
  Given a file with the name will-be-created-and-modified.txt does not exist in /
  When a client makes a PUT request to /will-be-created-and-modified.txt
  And the request contains the text/plain message body 
  """
  plain text body
  """
  Then the server should respond with status code 201 Created
  And a file with the name will-be-created-and-modified.txt exists in /

Scenario: PUT request to resource that does exist
  Given a file with the name will-be-created-and-modified.txt exists in /
  When a client makes a PUT request to /will-be-created-and-modified.txt
  And the request contains the text/plain message body
  """
  modification to plain text body
  """
  Then the server should respond with status code 200 OK
  And the file with the name will-be-created-and-modified.txt in / should contain the content "modification to plain text body"