package com.omarnyte.handler;

import java.util.HashMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.omarnyte.Directory;
import com.omarnyte.http.MessageHeader;
import com.omarnyte.request.HttpMethod;
import com.omarnyte.request.Request;
import com.omarnyte.response.HttpStatusCode;
import com.omarnyte.response.Response;
import com.omarnyte.util.ResponseUtil;
import com.omarnyte.util.Util;

public class FormHandler implements Handler {   
  private final static String DESTINATION_DIRECTORY_URI = "/POSTed";
  private final static String POSTED_FILE_EXTENSION = ".txt";
  private static final List<String> SUPPORTED_METHODS = Arrays.asList( 
    HttpMethod.OPTIONS, 
    HttpMethod.POST
  );
  
  private Directory directory;
  
  public FormHandler(Directory directory) {
    this.directory = directory;
    this.directory.createDirectory(DESTINATION_DIRECTORY_URI);
  }
  
  public Response generateResponse(Request request) {      
    switch (request.getMethod()) { 
      case HttpMethod.OPTIONS: 
        return ResponseUtil.buildOptionsResponse(SUPPORTED_METHODS);
      case HttpMethod.POST:  
        return buildPostResponse(request); 
      default:  
        return ResponseUtil.buildMethodNotAllowedResponse(SUPPORTED_METHODS);
    } 
  }

  private Response buildPostResponse(Request request) {
    try {
      String uri = DESTINATION_DIRECTORY_URI + "/" + Util.createRandomFileName(POSTED_FILE_EXTENSION);
      byte[] content = createFileContent(request);
      directory.createFileWithContent(uri, content);
      return new Response.Builder(HttpStatusCode.SEE_OTHER)
                         .setHeader(MessageHeader.LOCATION, uri)
                         .build();
    } catch(ArrayIndexOutOfBoundsException e) {
      return new Response.Builder(HttpStatusCode.BAD_REQUEST)
                         .build();
    }
  }

  private byte[] createFileContent(Request request) throws ArrayIndexOutOfBoundsException {
    String content =  "Thank you for submitting the following data:\n";
    HashMap<String, String> bodyKeyValPairs = extractKeyValPairsFromBody(request.getBody());
    content += stringifyKeyValPairs(bodyKeyValPairs);
    return content.getBytes();
  }
  
  private HashMap<String, String> extractKeyValPairsFromBody(String body) throws ArrayIndexOutOfBoundsException {
    HashMap<String, String> keyValPairs = new HashMap<String, String>();
    String[] splitBody = body.split("&");
    for (String keyValPair : splitBody) {
      String[] splitKeyValPair = keyValPair.split("=");
      String key = splitKeyValPair[0];
      String val = splitKeyValPair[1];
      keyValPairs.put(key, val);
    }
    
    return keyValPairs;
  }

  private String stringifyKeyValPairs(HashMap<String, String> keyValPairs) {
    String result = "";
    for (Map.Entry<String, String> entry : keyValPairs.entrySet()) {
      String key = entry.getKey();
      String value = entry.getValue();
      result += key + ": " + value + "\n";
    }

    return result;
  }

}
