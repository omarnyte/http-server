package com.omarnyte.handler;

import java.util.Arrays;
import java.util.List;

import com.omarnyte.Directory;
import com.omarnyte.http.MessageHeader;
import com.omarnyte.http.MimeType;
import com.omarnyte.request.HttpMethod;
import com.omarnyte.request.Request;
import com.omarnyte.response.HttpStatusCode;
import com.omarnyte.response.Response;
import com.omarnyte.util.ResponseUtil;
import com.omarnyte.util.Util;

public class PeopleHandler implements Handler {
  private final static String DESTINATION_DIRECTORY_URI = "/people";
  private final static String[] SUPPORTED_MEDIA_TYPES = { 
    MimeType.JSON, 
    MimeType.PLAIN_TEXT
  };
  private static final List<String> SUPPORTED_METHODS = Arrays.asList( 
    HttpMethod.OPTIONS,
    HttpMethod.POST
  );

  private Directory directory;

  public PeopleHandler(Directory directory) {
    this.directory = directory;
    this.directory.createDirectory(DESTINATION_DIRECTORY_URI);
  }

  public Response generateResponse(Request request) {
    switch (request.getMethod()) { 
      case HttpMethod.OPTIONS: 
        return ResponseUtil.buildOptionsResponse(SUPPORTED_METHODS);
      case HttpMethod.POST:  
        return handlePostRequest(request); 
      default:  
        return new Response.Builder(HttpStatusCode.METHOD_NOT_ALLOWED) 
                           .build(); 
    }    
  }

  private Response handlePostRequest(Request request) {
    String contentType = request.getHeader(MessageHeader.CONTENT_TYPE);
    if (Arrays.asList(SUPPORTED_MEDIA_TYPES).contains(contentType)) {
      String seeOtherUri = createFile(request);
      return buildPostResponse(seeOtherUri);
    } else {
      return buildUnsupportedMediaTypeResponse();
    }
  }

  private String createFile(Request request) {
    String contentType = request.getHeader(MessageHeader.CONTENT_TYPE);
    String extension = MimeType.getExtension(contentType);
    byte[] content = request.getBody().getBytes();
    String uri = DESTINATION_DIRECTORY_URI + "/" + Util.createRandomFileName(extension);
    this.directory.createFileWithContent(uri, content);
    return uri;
  }

  private Response buildPostResponse(String seeOtherUri) {
    return new Response.Builder(HttpStatusCode.CREATED)
                       .setHeader(MessageHeader.LOCATION, seeOtherUri)
                       .build();
  }

  private Response buildUnsupportedMediaTypeResponse() {
    return new Response.Builder(HttpStatusCode.UNSUPPORTED_MEDIA_TYPE)
                      .build();
  }

}
