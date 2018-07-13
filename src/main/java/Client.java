import java.io.*;
import java.net.*;

public class Client implements Runnable {
  private BufferedReader in;
  private PrintWriter out;
  private Socket client; 
  private Router router;
  
  Client(Socket clientSocket, Router router) {
    this.client = clientSocket; 
    this.router = router;
  }
  
  public void run() {
    try {
      initiateClient();
          
      String requestString = stringifyRequest();
  
      if (requestString == "") {
        return;
      }
      
      Request request = parseRequest(requestString);
      Response response = getResponse(request);
  
      out.print(response.toString());

      closeConnection();

    } catch (Exception e) {
      System.err.println(e);
    }
    
  }

  private void initiateClient() throws IOException {
    this.in = new BufferedReader(new InputStreamReader(client.getInputStream()));
    this.out = new PrintWriter(client.getOutputStream());
  }

  private String stringifyRequest() throws IOException {
    String requestString = "";
    String line;
    while ((line = this.in.readLine()) != null) {
      if (line.length() == 0) {
        break;
      } else {
        requestString += line;
      }
    }

    return requestString;
  }

  private Request parseRequest(String requestString) {
    RequestParser requestParser = new RequestParser(requestString);
    Request request = requestParser.generateRequest();
    return request;
  }

  private Response getResponse(Request request) {
    Handler handler = this.router.getHandler(request.getURI());
    Response response = handler.generateResponse(request);
    return response;
  }

  private void closeConnection() throws IOException {
    this.out.close(); 
    this.in.close(); 
    this.client.close(); 
  }

}