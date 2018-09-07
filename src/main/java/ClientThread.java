import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import java.util.Arrays;
import java.util.List;

public class ClientThread implements Runnable {
  private Authenticator authenticator; 
  private Socket clientSocket; 
  private BufferedReader in;
  private Logger logger;
  private LogFormatter logFormatter;
  private OutputStream out;
  private Router router;
  
  ClientThread(Socket clientSocket, Router router, Logger logger, Authenticator authenticator) {
    this.clientSocket = clientSocket; 
    this.router = router;
    this.logger = logger;
    this.logFormatter = new LogFormatter();
    this.authenticator = authenticator;
  }
  
  public void run() {
    try {
      initiateClient();

      Request request = applyRequestMiddleware();
      Response response = this.router.getResponse(request);
      byte[] formattedResponse = applyResponseMiddleware(response);
      
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

  private Request applyRequestMiddleware() throws BadRequestException {
    Request request = parseRequest();
    logRequest(request);
    return this.authenticator.authenticateRequest(request);
  }

  private Request parseRequest() throws BadRequestException {
    RequestParser requestParser = new RequestParser(this.in);
    return requestParser.generateRequest();
  }

  private void logRequest(Request request) {
    String requestFormattedForLogger = this.logFormatter.formatRequest(request);
    this.logger.logEntry(requestFormattedForLogger);
  }

  private byte[] applyResponseMiddleware(Response response) {
    logResponse(response);
    return new ResponseFormatter(response).formatResponse();
  }

  private void logResponse(Response response) {
    String responseFormattedForLogger = this.logFormatter.formatResponse(response);
    this.logger.logEntry(responseFormattedForLogger);
  }

  private void closeConnection() throws IOException {
    this.out.close(); 
    this.in.close(); 
    this.clientSocket.close(); 
  }

}
