package com.omarnyte.handler;

import com.omarnyte.request.Request;
import com.omarnyte.response.Response;

public interface Handler {

  public Response generateResponse(Request request);

}