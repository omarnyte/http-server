Feature: Cross Origin Resource Sharing 

Scenario Outline: GET request to a resource
  Given a file with the name <Name> exists in /
  When a client makes a GET request to <Path>
  Then the server should respond with status code 200 OK 
  And the server should respond with the header Access-Control-Allow-Origin http://localhost:8888

  Examples: 
  | Name            |  Path            | 
  | sample.txt      | /sample.txt      |
  | sample.json     | /sample.json     |
