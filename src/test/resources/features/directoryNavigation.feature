Feature: Directory and subdirectory navigation

Scenario: 
  When a client makes a GET request to /
  Then the server should respond with status code 200 OK 
  And the server should respond with a message body that contains href="/hello"

Scenario: 
  When a client makes a GET request to /hello
  Then the server should respond with status code 200 OK 
  And the server should respond with a message body that contains href="/hello/my"

Scenario: 
  When a client makes a GET request to /hello/my
  Then the server should respond with status code 200 OK 
  And the server should respond with a message body that contains href="/hello/my/name"

Scenario: 
  When a client makes a GET request to /hello/my/name
  Then the server should respond with status code 200 OK 
  And the server should respond with a message body that contains href="/hello/my/name/is"

Scenario: 
  When a client makes a GET request to /hello/my/name/is
  Then the server should respond with status code 200 OK 
  And the server should respond with the header Content-Type text/html
  And the server should respond with a message body that contains href="/hello/my/name/is/Omar"

Scenario: 
  When a client makes a GET request to /hello/my/file-inside-subdirectory.txt
  Then the server should respond with status code 200 OK 
  And the server should respond with the header Content-Type text/plain
  And the server should respond with a message body of "I live in a subdirectory."