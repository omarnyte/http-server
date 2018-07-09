import java.io.*;
import java.net.*;
import java.lang.Thread;

public class Server {
  private int port; 
  private Router router;

  Server(int port, Router router) {
    this.port = port;
    this.router = router;
  }

  public void start() {
    System.out.println("Listening on port " + this.port + ":");

    try {
      ServerSocket server = new ServerSocket(port);
      while (true) {
        Socket clientSocket = server.accept();
        (new Thread(new Client(clientSocket, router))).start();
      } 
    }
    catch (Exception e) {
      System.err.println(e);
      System.err.println("Error on port " + port);
    }
  }

  

}
