package com.omarnyte.handler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.omarnyte.http.MessageHeader;
import com.omarnyte.request.Request;
import com.omarnyte.response.HttpStatusCode;
import com.omarnyte.response.Response;

public class EchoHandler implements Handler {
  private static final String TIME_FORMAT = "hh:mm:ss";

  public Response generateResponse(Request request) {
    String method = request.getMethod();

    switch (method) {
      case "HEAD": 
        return buildHeadResponse();
      case "GET": 
        return buildGetResponse();
      default: 
        return new Response.Builder(HttpStatusCode.METHOD_NOT_ALLOWED)
                           .build();
    }
  }

  private Response buildHeadResponse() {
    String messageBody = createMessageBody();
    int contentLength = MessageHeader.determineContentLength(messageBody);
    return new Response.Builder(HttpStatusCode.OK)
                       .setHeader(MessageHeader.CONTENT_TYPE, "text/plain")
                       .setHeader(MessageHeader.CONTENT_LENGTH, contentLength)
                       .build();
  }

  private Response buildGetResponse() {
    String messageBody = createMessageBody();
    int contentLength = MessageHeader.determineContentLength(messageBody);
    return new Response.Builder(HttpStatusCode.OK)
                       .setHeader(MessageHeader.CONTENT_TYPE, "text/plain")
                       .setHeader(MessageHeader.CONTENT_LENGTH, contentLength)
                       .messageBody(messageBody)
                       .build();
  }

  private String createMessageBody() {
    return "Hello, world: " + getFormattedTime();
  }

  private String getFormattedTime() {
    DateFormat dateFormat = new SimpleDateFormat(TIME_FORMAT);
    return dateFormat.format(new Date());
  }
  
}
