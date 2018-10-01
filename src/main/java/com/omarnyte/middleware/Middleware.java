package com.omarnyte.middleware; 

import com.omarnyte.request.Request;
import com.omarnyte.response.Response;

public abstract class Middleware {
  private Middleware next;

  public Middleware linkWith(Middleware next) {
    this.next = next;
    return next;
  }

  public Request applyMiddleware(Request request) {
    return checkNext(request);
  }

  public Response applyMiddleware(Response response) {
    return checkNext(response);
  };

  protected Request checkNext(Request request) {
    if (next == null) {
        return request;
    }
    return next.applyMiddleware(request);
  }

  protected Response checkNext(Response response) {
    if (next == null) {
        return response;
    }
    return next.applyMiddleware(response);
  }

}  
