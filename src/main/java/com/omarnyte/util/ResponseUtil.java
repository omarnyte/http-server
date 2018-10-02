package com.omarnyte.util;

import java.util.List;

import com.omarnyte.http.MessageHeader;
import com.omarnyte.response.HttpStatusCode;
import com.omarnyte.response.Response;

public class ResponseUtil {

  public static Response buildMethodNotAllowedResponse(List<String> supportedMethods) {
    return new Response.Builder(HttpStatusCode.METHOD_NOT_ALLOWED) 
        .setHeader(MessageHeader.ALLOW, formatAllowHeaderVal(supportedMethods))
        .build(); 
  }

  public static Response buildOptionsResponse(List<String> supportedMethods) {
    return new Response.Builder(HttpStatusCode.OK)
        .setHeader(MessageHeader.ALLOW, formatAllowHeaderVal(supportedMethods))
        .build();
  }

  private static String formatAllowHeaderVal(List<String> supportedMethods) {
    return String.join(", ", supportedMethods);
  }
  
}
