Feature: OPTIONS Request Handling

Scenario: OPTIONS request to the general server 
  When a client makes an OPTIONS request to /*
  Then the server should respond with status code 200 OK 
  And the server should respond with the header Allow DELETE, GET, HEAD, OPTIONS, PATCH, POST, PUT

Scenario Outline: OPTIONS request to an API endpoint
  When a client makes an OPTIONS request to <Path>
  Then the server should respond with status code 200 OK 
  And the server should respond with the header Allow <Supported Methods>
  |  Path        | Supported Methods  |
  | /api/form    | OPTIONS, POST      | 
  | /api/people  | OPTIONS, POST      | 
  | /api/query   | GET, HEAD, OPTIONS | 