package com.omarnyte;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import com.omarnyte.exception.BadRequestException;
import com.omarnyte.middleware.Middleware;
import com.omarnyte.request.Request;
import com.omarnyte.request.RequestParser;
import com.omarnyte.response.Response;
import com.omarnyte.response.ResponseFormatter;

public class ClientThread implements Runnable {
  private Socket clientSocket; 
  private BufferedReader in;
  private Middleware middleware;
  private OutputStream out;
  private Router router;
  
  ClientThread(Socket clientSocket, Router router, Middleware middleware) {
    this.clientSocket = clientSocket; 
    this.router = router;
    this.middleware = middleware;
  }
  
  public void run() {
    try {
      initiateClient();

      Request request = parseRequest();
      request = this.middleware.applyMiddleware(request);

      Response response = this.router.getResponse(request);
      response = this.middleware.applyMiddleware(response);
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
