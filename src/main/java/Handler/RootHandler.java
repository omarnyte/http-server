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
      case "GET": 
        return buildGetResponse();
      default: 
        return new Response.Builder(HttpStatusCode.METHOD_NOT_ALLOWED)
                           .build();
    }
  }

  private Response buildGetResponse() {
    String messageBody = createMessageBody();
    int contentLength = ResponseHeader.determineContentLength(messageBody);
    return new Response.Builder(HttpStatusCode.OK)
                       .contentType("text/plain")
                       .contentLength(contentLength)
                       .messageBody(messageBody)
                       .build(); 
  }

  private String createMessageBody() {
    return this.store.listContent();
  }

}
