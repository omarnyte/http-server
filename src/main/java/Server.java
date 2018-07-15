import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class Server {
  private int port; 
  private Router router;

  public Server(int port, Router router) {
    this.port = port;
    this.router = router;
  }

  public void start() {
    try {
      ServerSocket server = new ServerSocket(port);
      ExecutorService executor = Executors.newCachedThreadPool();
      while (true) {
        Socket clientSocket = server.accept();
        executor.execute(new ClientThread(clientSocket, this.router));
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

}
