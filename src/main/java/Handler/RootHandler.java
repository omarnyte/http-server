import java.io.File;

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
        return new Response.Builder(HttpStatusCode.OK)
                           .messageBody(createMessageBody())
                           .build();
      default: 
        return new Response.Builder(HttpStatusCode.METHOD_NOT_ALLOWED)
                           .build();
    }
  }

  public String createMessageBody() {
    return this.store.listContent();
  }

}
  
