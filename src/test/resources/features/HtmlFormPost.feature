Feature: HTML Form POST
Background: 
  Given a file with the name POSTable-form.html exists in /
  And the file contains the content 
  """
  <!DOCTYPE html>
  <html>
  <head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>HTML Form</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
  </head>

  <body>
    <form method="post" action="/api/form">
      Text Input:<br>
      <input type="text" name="textInput"><br>
      Radio Input:<br>
      <input type="radio" name="radioInput" value="first" checked>First<br>
      <input type="radio" name="radioInput" value="second">Second<br>
      <input type="radio" name="radioInput" value="third">Third<br>
      <input type="submit" name="submit"> 
    </form>
  </body>
  </html>
  """
   
Scenario: A client can fill out a form and submit it 
  When a client makes a POST request to /api/form
  And the request contains the application/x-www-form-urlencoded message body 
  """
  aKey=aValue&anotherKey=anotherValue
  """
  Then the server should respond with status code 200 OK
  And the server should respond with a message body of 
  """ 
  Thank you for submitting the following data:
  anotherKey: anotherValue
  aKey: aValue
  """ 
