Feature: Directory and subdirectory navigation
Background: 
  Given a directory /hello/my/name/is/Omar exists

Scenario Outline: GET request to subdirectories 
  When a client makes a GET request to <Path> 
  Then the server should respond with status code 200 OK
  And the server should respond with a message body that contains href=<Anchor Link>

  Examples: 
  | Path           | Anchor Link       |
  | /              | /hello            |
  | /hello         | /hello/my         |
  | /hello/my      | /hello/my/name    |
  | /hello/my/name | /hello/my/name/is |

Scenario: GET request to file located inside a subdirectory
  Given a file with the name file-inside-subdirectory.txt exists in /hello/my
  And the file contains the content 
  """
  I live in a subdirectory.
  """
  When a client makes a GET request to /hello/my/file-inside-subdirectory.txt
  Then the server should respond with status code 200 OK 
  And the server should respond with the header Content-Type text/plain
  And the server should respond with a message body of 
  """
  I live in a subdirectory.
  """