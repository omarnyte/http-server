 public class FileHandler implements Handler {
  private DataStore store;
  private String uri;
  
  public FileHandler(DataStore store) {
    this.store = store;
  }
  
  public Response generateResponse(Request request) { 
    this.uri = request.getURI();
     
    switch (request.getMethod()) { 
      case "GET":  
        return buildGETResponse(); 
      default:  
        return new Response.Builder(HttpStatusCode.METHOD_NOT_ALLOWED) 
                           .build(); 
    } 
  }

  private Response buildGETResponse() { 
    int statusCode = determineStatusCode();
    String messageBody = createMessageBody(statusCode);
     
    return new Response.Builder(statusCode) 
                       .messageBody(messageBody) 
                       .build(); 
  } 

  private int determineStatusCode() {
    return store.existsInStore(this.uri) ? HttpStatusCode.OK : HttpStatusCode.NOT_FOUND;
  }

  private String createMessageBody(int statusCode) {
    String emptyMessageBody = "";
    return (statusCode == HttpStatusCode.OK) ? store.read(this.uri) : emptyMessageBody;
  }

}
