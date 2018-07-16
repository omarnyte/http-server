import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
  private Socket client;
  private BufferedReader in;
  private PrintWriter out; 
  private int port; 
  private Router router;
  private ServerSocket server;

  public Server(int port, Router router) {
    this.port = port;
    this.router = router;
  }

  public void start() {
    try {
      this.server = new ServerSocket(port);
      System.out.println("Listening on port " + this.port + ":");
      while (true) {
        initiateClient();

        String requestString = stringifyRequest();

        if (requestString == "") {
          continue;
        }
        
        Request request = parseRequest(requestString);
        Response response = this.router.getResponse(request);
 
        out.print(response.toString());

        closeConnection();
      } 
    } catch (BindException e) {
      System.err.println("Port " + port + " is unavailable.");
    } catch (IllegalArgumentException e) {
      System.err.println("Port number must be between 0 and 65535.");
    } catch (Exception e) {
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

  private Request parseRequest(String requestString) throws BadRequestException {
    RequestParser requestParser = new RequestParser(requestString);
    Request request = requestParser.generateRequest();
    return request;
  }

  private void closeConnection() throws IOException {
    this.out.close(); 
    this.in.close(); 
    this.client.close(); 
  }

}
