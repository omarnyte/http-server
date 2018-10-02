package com.omarnyte.mock;

import com.omarnyte.middleware.Middleware;
import com.omarnyte.response.Response;

public class MockMiddleware extends Middleware {
  private Response expectedResponse;
  
  public MockMiddleware(Response expectedResponse) {
    this.expectedResponse = expectedResponse;
  }
  
  public Response applyMiddleware(Response response) {
    return this.expectedResponse;
  }
  
}
