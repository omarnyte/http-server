import java.io.BufferedReader;
import java.io.IOException;

public class RequestParser {
  BufferedReader reader; 

  public RequestParser(BufferedReader reader) {
    this.reader = reader;
  }

  public Request generateRequest() throws BadRequestException {
    try {
      Request.Builder requestBuilder = new Request.Builder();
      buildRequestLine(requestBuilder);
      buildHeaders(requestBuilder);
      buildMessageBody(requestBuilder);
      return requestBuilder.build();
    } catch (ArrayIndexOutOfBoundsException e) {
      throw new BadRequestException("Bad request from ArrayIndex!");
    } catch (IOException e) {
      throw new BadRequestException("Bad request from IOException!");
    }
  }

  private Request.Builder buildRequestLine(Request.Builder requestBuilder) throws IOException {
    String firstLine = this.reader.readLine();
    RequestLine requestLine = new RequestLine(firstLine);
    return requestBuilder.method(requestLine.getMethod())
                         .uri(requestLine.getURI())
                         .version(requestLine.getHTTPVersion());
  }

  private Request.Builder buildHeaders(Request.Builder requestBuilder) throws IOException {
    String line;
    while ((line = this.reader.readLine()) != null) {
      if (line.length() == 0) {
        break;
      } else {
        String[] splitLine = line.split(": ");
        String key = splitLine[0];
        String val = splitLine[1];
        requestBuilder.setHeader(key, val);
      }
    }

    return requestBuilder;
  }

  private Request.Builder buildMessageBody(Request.Builder requestBuilder) throws IOException {
    String stringifiedMessageBody = stringifyMessageBody();
    if (stringifiedMessageBody.length() > 0) {
      String[] splitMessageBody = stringifiedMessageBody.split("&");
      for (String keyValPair : splitMessageBody) {
        System.out.println("keyValPair: " + keyValPair);
        String[] splitKeyValPair = keyValPair.split("=");
        String key = splitKeyValPair[0];
        String val = splitKeyValPair[1];
        requestBuilder.addMessageBodyKeyVal(key, val);
      }
    } 

    return requestBuilder;
  }

  private String stringifyMessageBody() throws IOException {
    StringBuilder stringBuilder = new StringBuilder();
    while (this.reader.ready()) {
      stringBuilder.append((char) (this.reader.read()));
    }
    
    return stringBuilder.toString();
  }

}
