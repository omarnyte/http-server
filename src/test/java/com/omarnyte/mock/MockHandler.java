package com.omarnyte.mock;

import com.omarnyte.request.Request;
import com.omarnyte.response.Response;

import com.omarnyte.handler.Handler;

public class MockHandler implements Handler {
  Response response;
  
  public MockHandler(Response response) {
    this.response = response; 
  }

  public Response generateResponse(Request request) {
    return response;
  }
  
}
