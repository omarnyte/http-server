package com.omarnyte;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

import com.omarnyte.middleware.Middleware;

public class Server {
  private Middleware middleware; 
  private int port; 
  private Router router;
  ServerSocket server;

  public Server(int port, Router router, Middleware middleware) {
    this.port = port;
    this.router = router;
    this.middleware = middleware;
  }

  public void start() {
    try {
      this.server = new ServerSocket(this.port);
      System.out.println("Listening on port " + this.port);
      ExecutorService executor = Executors.newCachedThreadPool();
      while (true) {
        Socket clientSocket = server.accept();
        executor.execute(new ClientThread(clientSocket, this.router, this.middleware));
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

  public void close() throws IOException {
    System.out.println("Closing ServerSocket on port " + port);
    this.server.close();
  }
  
}
