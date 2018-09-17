Feature: Image Negotiation
Background: 
  Given a file with the name image.jpg exists in /

Scenario: The image file exists 
  When a client makes a GET request to /image.jpg
  Then the server should respond with status code 200 OK 
  And the server should response with the content of the image

Scenario: The image file does not exist
  When a client makes a GET request to /does-not-exist.jpg
  Then the server should respond with status code 404 Not Found

Scenario: HEAD request to an existing image file
  When a client makes a HEAD request to /image.jpg
  Then the server should respond with status code 200 OK 
  And the server should respond with the header Content-Type image/jpeg
  And the server should not respond with a message body
