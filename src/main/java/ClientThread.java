import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class ClientThread implements Runnable {
  private BufferedReader in;
  private OutputStream out;
  private Socket clientSocket; 
  private Router router;
  
  ClientThread(Socket clientSocket, Router router) {
    this.clientSocket = clientSocket; 
    this.router = router;
  }
  
  public void run() {
    try {
      initiateClient();
          
      Request request = parseRequest();
      Response response = this.router.getResponse(request);
      byte[] formattedResponse = new ResponseFormatter(response).formatResponse();
      this.out.write(formattedResponse);

      closeConnection();
    } catch (Exception e) {
      System.err.println(e);
    }
    
  }

  private void initiateClient() throws IOException {
    this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    this.out = clientSocket.getOutputStream();
  }

  private Request parseRequest() throws BadRequestException {
    RequestParser requestParser = new RequestParser(this.in);
    return requestParser.generateRequest();
  }

  private void closeConnection() throws IOException {
    this.out.close(); 
    this.in.close(); 
    this.clientSocket.close(); 
  }

}
