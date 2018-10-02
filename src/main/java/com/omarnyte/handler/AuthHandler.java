package com.omarnyte.handler;

import com.omarnyte.http.MessageHeader;
import com.omarnyte.request.Request;
import com.omarnyte.response.HttpStatusCode;
import com.omarnyte.response.Response;

public class AuthHandler implements Handler {
  private final static String AUTH_SCHEME = "Basic";
  
  public Response generateResponse(Request request) {
    return new Response.Builder(HttpStatusCode.UNAUTHORIZED)
                       .setHeader(MessageHeader.AUTHENTICATE, AUTH_SCHEME)
                       .build();
  }

}
