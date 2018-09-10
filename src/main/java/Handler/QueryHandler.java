import java.util.HashMap;

public class QueryHandler implements Handler {
  private UrlDecoder urlDecoder;

  public QueryHandler(UrlDecoder urlDecoder) {
    this.urlDecoder = urlDecoder;
  }
  
  public Response generateResponse(Request request) {
    switch (request.getMethod()) {
      case "HEAD": 
        return buildHeadResponse(request);
      case "GET": 
        return buildGetResponse(request);
      default: 
        return new Response.Builder(HttpStatusCode.METHOD_NOT_ALLOWED)
                           .build();
    }
  }
  
  private Response buildHeadResponse(Request request) {
    String messageBody = createMessageBody(request.getQuery());
    int contentLength = MessageHeader.determineContentLength(messageBody);
    HashMap<String, String> headers = getHeaders(contentLength);
    return new Response.Builder(HttpStatusCode.OK)
                       .headers(headers)
                       .build();
  }

  private Response buildGetResponse(Request request) {
    try {
      String messageBody = createMessageBody(request.getQuery());
      int contentLength = MessageHeader.determineContentLength(messageBody);
      HashMap<String, String> headers = getHeaders(contentLength);
      return new Response.Builder(HttpStatusCode.OK) 
                         .headers(headers)
                         .messageBody(messageBody)
                         .build();
    } catch (ArrayIndexOutOfBoundsException e) {
      return new Response.Builder(HttpStatusCode.BAD_REQUEST)
                         .build();
    }
  }

  private HashMap<String, String> getHeaders(int contentLength) {
    HashMap<String, String> headers = new HashMap<String, String>();
    headers.put(MessageHeader.CONTENT_TYPE, MimeType.PLAIN_TEXT);
    headers.put(MessageHeader.CONTENT_LENGTH, Integer.toString(contentLength));
    return headers;
  }

  private String createMessageBody(String query) {
      String body = "";
      
      String[] splitQuery = query.split("&");
      for (String paramPair : splitQuery) {
        String[] splitParameters = paramPair.split("=");
        String key = splitParameters[0];
        String val = splitParameters[1];
        String decodedKey = urlDecoder.decodeString(key);
        String decodedVal = urlDecoder.decodeString(val);
        body += decodedKey + " : " + decodedVal + "\n";
      }
  
      return body;
  }

}