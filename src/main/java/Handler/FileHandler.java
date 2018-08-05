import java.io.UnsupportedEncodingException;

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
        return buildGetResponse(); 
      default:  
        return new Response.Builder(HttpStatusCode.METHOD_NOT_ALLOWED) 
                           .build(); 
    } 
  }

  private Response buildGetResponse() {
    int statusCode = determineStatusCode();
    byte[] messageBody = createMessageBody();
    int contentLength = messageBody.length;
    String contentType = determineContentType();
    return new Response.Builder(statusCode)
                       .messageBody(messageBody)
                       .setHeader(ResponseHeader.CONTENT_LENGTH, contentLength)
                       .setHeader(ResponseHeader.CONTENT_TYPE, contentType)
                       .build();
  }

  private int determineStatusCode() {
    return this.store.existsInStore(this.uri) ? HttpStatusCode.OK : HttpStatusCode.NOT_FOUND;
  }

  private byte[] createMessageBody() {
    return this.store.existsInStore(this.uri) ? store.readFile(this.uri) : buildNotFoundMessage().getBytes();
  }

  private String buildNotFoundMessage() {
    return this.uri + " was not found!";
  }

  private String determineContentType() {
    return this.store.existsInStore(this.uri) ? this.store.getFileType(this.uri) : "text/plain";
  } 

}
