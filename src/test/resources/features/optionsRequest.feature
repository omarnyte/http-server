Feature: OPTIONS Request Handling

Scenario: OPTIONS request to the general server 
  When a client makes an OPTIONS request to /*
  Then the server should respond with status code 200 OK 
  And the server should respond with the header Allow DELETE, GET, HEAD, OPTIONS, PATCH, POST, PUT

Scenario Outline: OPTIONS request to directories
  When a client makes an OPTIONS request to <Path>
  Then the server should respond with status code 200 OK 
  And the server should respond with the header Allow GET, HEAD, OPTIONS

  Examples:
  |  Path   |
  | /       | 
  | /hello  | 

Scenario Outline: OPTIONS request to an API endpoint
  When a client makes an OPTIONS request to <Path>
  Then the server should respond with status code 200 OK 
  And the server should respond with the header Allow <Supported Methods>

  Examples: 
  |  Path        | Supported Methods  |
  | /api/form    | OPTIONS, POST      | 
  | /api/people  | OPTIONS, POST      | 
  | /api/query   | GET, HEAD, OPTIONS | 

Scenario Outline: OPTIONS request to files
  Given a file with the name <File Name> exists in <Directory>
  When a client makes an OPTIONS request to <Path>
  Then the server should respond with status code 200 OK 
  And the server should respond with the header Allow <Supported Methods>

  Examples: Existing files
  | File Name   | Directory |  Path        | Supported Methods |
  | sample.json | /         | /sample.json | DELETE, GET, HEAD, OPTIONS, PATCH, PUT | 
  | sample.txt  | /         | /sample.txt  | DELETE, GET, HEAD, OPTIONS, PATCH, PUT | 

Scenario Outline: OPTIONS request to nonexistent files
  Given a file with the name <File Name> does not exist in <Directory>
  When a client makes an OPTIONS request to <Path>
  Then the server should respond with status code 200 OK 
  And the server should respond with the header Allow <Supported Methods>

  Examples: Existing files
  | File Name           | Directory |  Path               |Supported Methods|
  | does-not-exist.json | /         | /does-not-exist.json | OPTIONS, PUT   | 
  | does-not-exist.txt  | /         | /does-not-exist.txt  | OPTIONS, PUT   | 
  | does-not-exist.jpg  | /         | /does-not-exist.jpg  | OPTIONS        | 
