import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread implements Runnable {
  private BufferedReader in;
  private PrintWriter out;
  private Socket clientSocket; 
  private Router router;
  
  ClientThread(Socket clientSocket, Router router) {
    this.clientSocket = clientSocket; 
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
      Response response = this.router.getResponse(request);
      String formattedResponse = new ResponseFormatter(response).formatResponse();

      out.print(formattedResponse);

      closeConnection();

    } catch (Exception e) {
      System.err.println(e);
    }
    
  }

  private void initiateClient() throws IOException {
    this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    this.out = new PrintWriter(clientSocket.getOutputStream());
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

  private Request parseRequest(String requestString) throws BadRequestException {
    RequestParser requestParser = new RequestParser(requestString);
    return requestParser.generateRequest();
  }

  private void closeConnection() throws IOException {
    this.out.close(); 
    this.in.close(); 
    this.clientSocket.close(); 
  }

}