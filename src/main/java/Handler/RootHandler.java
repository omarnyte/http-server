import java.io.File;
import java.io.UnsupportedEncodingException;

public class RootHandler implements Handler {
  private Response response;
  private DataStore store;
  
  public RootHandler(DataStore store) {
    this.store = store;
  }
  
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
    int contentLength = ResponseHeader.determineContentLength(messageBody);
    return new Response.Builder(HttpStatusCode.OK)
                       .setHeader(ResponseHeader.CONTENT_LENGTH, contentLength)
                       .setHeader(ResponseHeader.CONTENT_TYPE, "text/html")
                       .build(); 
  }

  private Response buildGetResponse() {
    String messageBody = createMessageBody();
    int contentLength = ResponseHeader.determineContentLength(messageBody);
    return new Response.Builder(HttpStatusCode.OK)
                       .setHeader(ResponseHeader.CONTENT_LENGTH, contentLength)
                       .setHeader(ResponseHeader.CONTENT_TYPE, "text/html")
                       .messageBody(messageBody)
                       .build(); 
  }

  private String createMessageBody() {
    String htmlResponse = "";
    String[] files = this.store.listContent();
    for (String file : files) {
      htmlResponse += String.format(
        "<a href=\"/%s\">%s</a><br>", file, file);
    } 

    return htmlResponse;
  }

}
