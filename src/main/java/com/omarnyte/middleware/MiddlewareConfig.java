package com.omarnyte.middleware;

import java.util.Arrays;
import java.util.List;

import com.omarnyte.CorsMiddleware;
import com.omarnyte.authentication.Authenticator;
import com.omarnyte.authentication.Credentials;
import com.omarnyte.exception.LoggerException;
import com.omarnyte.logger.Logger;
import com.omarnyte.middleware.Middleware;

public class MiddlewareConfig {
  private final static String AUTH_ROUTE = "/api/authenticate";
  private static final String LOG_DIRECTORY_PATH = System.getProperty("user.dir") + "/logs";

  public Middleware getMiddlewareChain() throws LoggerException {
    CorsMiddleware corsMiddleware = new CorsMiddleware();
    Logger logger = setUpLogger();
    Authenticator authenticator = setUpAuthenticator();

    Middleware middleware = corsMiddleware;
    middleware.linkWith(logger)
              .linkWith(authenticator);
    return middleware;
  }

  private Logger setUpLogger() throws LoggerException {
      String dateTimePattern = "yyyymmddhhmmss";
      Logger logger = new Logger(LOG_DIRECTORY_PATH, dateTimePattern);
      logger.createLogFile();
      return logger;
  }

  private Authenticator setUpAuthenticator() {
    Credentials credentials = new Credentials("username", "password");
    List<String> protectedUris = Arrays.asList(
      "/protected"
    ); 
    return new Authenticator(credentials, protectedUris, AUTH_ROUTE);
  }
  
}
