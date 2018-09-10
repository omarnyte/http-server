Feature: Query Parameter Parsing 

Scenario: GET request to /api/query with single parameter and no reserved characters
  When a client makes a GET request to /api/query?firstKey=firstValue
  Then the server should respond with status code 200 OK
  And the server should respond with a message body of "firstKey : firstValue"

Scenario: GET request to /api/query with multiple parameters and no reserved characters
  When a client makes a GET request to /api/query?firstKey=firstValue&secondKey=secondValue
  Then the server should respond with status code 200 OK
  And the server should respond with a message body of
  """"
  firstKey : firstValue
  secondKey : secondValue
  """
  
Scenario: GET request to /api/query with single parameter and reserved characters
  When a client makes a GET request to /api/query?key%20with%20encoded%20words%20%26%20characters=%5Bvalue%20with%20encoded%20characters%5D
  Then the server should respond with status code 200 OK
  And the server should respond with a message body of "key with encoded words & characters : [value with encoded characters]"

Scenario: GET request to /api/query with multiple parameters and reserved characters
  When a client makes a GET request to /api/query?encodedCharacters=%3A%2F%3F%23%5B%5D%40%21%24%26%27%28%29%2A%2B%2C%3B%3D%25%20&nonEncodedCharacters=value
  Then the server should respond with status code 200 OK
  And the server should respond with a message body of 
  """
  encodedCharacters : :/?#[]@!$&'()*+,;=% 
  nonEncodedCharacters : value
  """  
