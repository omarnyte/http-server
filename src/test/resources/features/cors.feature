Feature: Cross Origin Resource Sharing 

Scenario Outline: GET request to a resource
  When a client makes a GET request to <Path>
  Then the server should respond with status code 200 OK 
  And the server should respond with the header Access-Control-Allow-Origin *

  Examples: 
  |  Path              | 
  | /cat-and-dog.jpg   |
  | /sample.txt        | 
  | /sample.json       | 
