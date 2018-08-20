import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

public class RequestParser {
  BufferedReader reader; 

  public RequestParser(BufferedReader reader) {
    this.reader = reader;
  }

  public Request generateRequest() throws BadRequestException {
    RequestLine requestLine = parseRequestLine();
    HashMap<String, String> headers = parseHeaders();
    HashMap<String, String> messageBody = parseMessageBody();
    
    return new Request.Builder()
                      .method(requestLine.getMethod())
                      .uri(requestLine.getURI())
                      .version(requestLine.getHTTPVersion())
                      .headers(headers)
                      .messageBody(messageBody)
                      .build();
  }

  private RequestLine parseRequestLine() throws BadRequestException {
    try {
      String requestLineString = this.reader.readLine();
      return new RequestLine(requestLineString);
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

  private HashMap<String, String> putLineInHeaders(String headersLine, HashMap<String, String> headers) {
    HashMap<String, String> updatedHeaders = headers;
    String[] splitLine = headersLine.split(": ");
    String field = splitLine[0];
    String val = splitLine[1];
    updatedHeaders.put(field, val);

    return updatedHeaders;
  }

  private HashMap<String, String> parseMessageBody() throws BadRequestException {
    try {
      String stringifiedMessageBody = stringifyMessageBody();
      HashMap<String, String> messageBody = new HashMap<String, String>();
      if (stringifiedMessageBody.length() > 0) {
        putStringifiedBodyInMessageBody(stringifiedMessageBody, messageBody);
      } 
  
      return messageBody;
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
  
  private HashMap<String, String> putStringifiedBodyInMessageBody(String stringifiedMessageBody, HashMap<String, String> messageBody) {
    HashMap<String, String> updatedMessageBody = messageBody;
    
    String[] splitMessageBody = stringifiedMessageBody.split("&");
    for (String keyValPair : splitMessageBody) {
      String[] splitKeyValPair = keyValPair.split("=");
      String key = splitKeyValPair[0];
      String val = splitKeyValPair[1];
      updatedMessageBody.put(key, val);
    }

    return updatedMessageBody;
  }

}
