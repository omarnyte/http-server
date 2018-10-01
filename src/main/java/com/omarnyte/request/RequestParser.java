package com.omarnyte.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

import com.omarnyte.exception.BadRequestException;
import com.omarnyte.http.MessageHeader;
import com.omarnyte.request.Request;

public class RequestParser {  
  BufferedReader reader; 

  public RequestParser(BufferedReader reader) {
    this.reader = reader;
  }

  public Request generateRequest() throws BadRequestException {
    RequestLineParser requestLineParser = parseRequestLine();
    HashMap<String, String> headers = parseHeaders();
    String method = extractMethod(requestLineParser.getMethod(), headers);
    String body = parseBody();
    
    return new Request.Builder()
                      .method(method)
                      .uri(requestLineParser.getURI())
                      .query(requestLineParser.getQuery())
                      .version(requestLineParser.getHTTPVersion())
                      .headers(headers)
                      .body(body)
                      .build();
  }

  private RequestLineParser parseRequestLine() throws BadRequestException {
    try {
      String requestLineString = this.reader.readLine();
      return new RequestLineParser(requestLineString);
    } catch (IOException e) {
      throw new BadRequestException("Could not parse request line.");
    } 
  }

  private HashMap<String, String> parseHeaders() throws BadRequestException {
    try {
      HashMap<String, String> headers = new HashMap<String, String>();
      String line;
      while ((line = this.reader.readLine()) != null) {
        if (line.length() == 0) {
          break;
        } else {
          headers = putLineInHeaders(line, headers);
        }
      }
      return headers;
    } catch (ArrayIndexOutOfBoundsException | IOException e) {
      throw new BadRequestException("Could not parse request headers.");
    }
  }

  private String extractMethod(String givenMethod, HashMap<String, String> headers) {
    String method = givenMethod;
    if (headers.containsKey(MessageHeader.METHOD_OVERRIDE)) {
      method = headers.get(MessageHeader.METHOD_OVERRIDE);
    }

    return method;
  }

  private HashMap<String, String> putLineInHeaders(String headersLine, HashMap<String, String> headers) {
    HashMap<String, String> updatedHeaders = headers;
    String[] splitLine = headersLine.split(": ");
    String field = splitLine[0];
    String val = splitLine[1];
    updatedHeaders.put(field, val);

    return updatedHeaders;
  }

  private String parseBody() throws BadRequestException {
    try {
      String stringifiedMessageBody = stringifyMessageBody();
      return (stringifiedMessageBody.length() > 0) ? stringifiedMessageBody : "";
      } catch (ArrayIndexOutOfBoundsException | IOException e) {
      throw new BadRequestException("Could not parse request message body.");
    }
  }

  private String stringifyMessageBody() throws IOException {
    StringBuilder stringBuilder = new StringBuilder();
    while (this.reader.ready()) {
      stringBuilder.append((char) (this.reader.read()));
    }
    
    return stringBuilder.toString();
  }
  
}
