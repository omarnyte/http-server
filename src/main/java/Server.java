import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class Server {
  private Authenticator authenticator;
  private int port; 
  private Logger logger;
  private Router router;
  ServerSocket server;

  public Server(int port, Router router, Logger logger, Authenticator authenticator) {
    this.port = port;
    this.router = router;
    this.logger = logger;
    this.authenticator = authenticator;
  }

  public void start() {
    try {
      this.server = new ServerSocket(this.port);
      System.out.println("Listening on port " + this.port);
      logger.logEntry("Server started on port " + this.port);
      ExecutorService executor = Executors.newCachedThreadPool();
      while (true) {
        Socket clientSocket = server.accept();
        executor.execute(new ClientThread(clientSocket, this.router, logger, this.authenticator));
      } 
    } catch (BindException e) {
      System.err.println("Port " + port + " is unavailable.");
    } catch (IllegalArgumentException e) {
      System.err.println("Port number must be between 0 and 65535.");
    } catch (IOException e) {
      System.err.println(e);
      System.err.println("Could not create logger");
    } catch (Exception e) {
      System.err.println(e);
      System.err.println("Error on port " + port);
    }
  }

  public void close() throws IOException {
    System.out.println("Closing ServerSocket on port " + port);
    this.server.close();
  }
  
}
