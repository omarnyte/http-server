package com.omarnyte.authentication;

import java.util.Base64;
import java.util.List;

import com.omarnyte.http.MessageHeader;
import com.omarnyte.middleware.Middleware;
import com.omarnyte.request.Request;

public class Authenticator extends Middleware {  
  private String authRoute;
  private Credentials credentials;
  private List<String> protectedUris;
  
  public Authenticator(Credentials credentials, List<String> protectedUris, String authRoute) {
    this.credentials = credentials;
    this.protectedUris = protectedUris;
    this.authRoute = authRoute;
  }

  public Request applyMiddleware(Request request) {
    try {
      return isProtected(request.getURI()) ? validateCredentials(request) : request;
    } catch (NullPointerException e) {
      return buildRequestToAuthRoute(request.getMethod());
    } 
  }

  private boolean isProtected(String uri) {
    return this.protectedUris.contains(uri) || isProtectedChild(uri);
  }

  private boolean isProtectedChild(String uri) {
    for (String protectedUri : protectedUris) {
      if (uri.matches(protectedUri + "/.+")) return true;
    }

    return false;
  }

  private Request validateCredentials(Request request) {
    String authHeader = request.getHeader(MessageHeader.AUTHORIZATION);
    if (authHeader == null) {
      return buildRequestToAuthRoute(request.getMethod());
    } else {
      String[] splitDecodedCredentials = getSplitDecodedCredentials(authHeader);
      return identicalCredentials(splitDecodedCredentials) ? request : buildRequestToAuthRoute(request.getMethod());  
    }
  }

  private String[] getSplitDecodedCredentials(String authHeader) throws NullPointerException {
    String[] splitAuthHeader = authHeader.split(" ");
    String encodedCredentials = splitAuthHeader[1];
    Base64.Decoder decoder = Base64.getDecoder();
    String decodedCredentials = new String(decoder.decode(encodedCredentials));
    return decodedCredentials.split(":");
  }

  private boolean identicalCredentials(String[] splitDecodedCredentials) {
    String username = splitDecodedCredentials[0];
    String password = splitDecodedCredentials[1];
    return this.credentials.areValidCredentials(username, password);
  }

  private Request buildRequestToAuthRoute(String method) {
    return new Request.Builder()
                      .method(method)
                      .uri(this.authRoute)
                      .build();
  }
  
} 
