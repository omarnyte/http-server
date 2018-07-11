import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Server {
  private Socket client;
  private BufferedReader in;
  private PrintWriter out; 
  private int port; 
  private Router router;
  private ServerSocket server;

  Server(int port, Router router) {
    this.port = port;
    this.router = router;
  }

  public void start() {
    System.out.println("Listening on port " + this.port + ":");

    try {
      this.server = new ServerSocket(port);
      while (true) {
        initiateClient();

        String requestString = stringifyRequest();

        if (requestString == "") {
          continue;
        }
        
        Request request = parseRequest(requestString);
        Response response = getResponse(request);
 
        out.print(response.toString());

        closeConnection();
      } 
    }
    catch (Exception e) {
      System.err.println(e);
      System.err.println("Error on port " + port);
    }
  }

  private void initiateClient() throws IOException {
    this.client = this.server.accept();
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
    return handler.generateResponse(request);
  }

  private void closeConnection() throws IOException {
    this.out.close(); 
    this.in.close(); 
    this.client.close(); 
  }

}
