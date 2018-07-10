import java.io.*;
import java.net.*;
import java.lang.Thread;
import java.util.concurrent.*;

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
      ExecutorService executor = Executors.newCachedThreadPool();
      while (true) {
        Socket clientSocket = server.accept();
        executor.execute(new Client(clientSocket, this.router));
      } 
    }
    catch (Exception e) {
      System.err.println(e);
      System.err.println("Error on port " + port);
    }
  }

}
