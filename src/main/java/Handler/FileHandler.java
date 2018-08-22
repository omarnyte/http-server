import java.io.UnsupportedEncodingException;

 public class FileHandler implements Handler {   
  private DataStore store;
  
  public FileHandler(DataStore store) {
    this.store = store;
  }
  
  public Response generateResponse(Request request) { 
    String uri = request.getURI();

    if (!this.store.existsInStore(uri)) {
      return buildNotFoundResponse(uri);
    }
     
    switch (request.getMethod()) { 
      case "HEAD":  
        return buildHeadResponse(uri); 
      case "GET":  
        return buildGetResponse(uri); 
      case "DELETE":  
        return this.store.deleteFile(uri) ? buildDeleteResponse() : buildInternalServerErrorResponse();
      default:  
        return new Response.Builder(HttpStatusCode.METHOD_NOT_ALLOWED) 
                           .build(); 
    } 
  }

  private Response buildNotFoundResponse(String uri) {
    return new Response.Builder(HttpStatusCode.NOT_FOUND)
                       .messageBody(buildNotFoundMessage(uri))
                       .build();
  }

  private Response buildHeadResponse(String uri) {
    int statusCode = determineStatusCode(uri);
    byte[] messageBody = createMessageBody(uri);
    int contentLength = messageBody.length;
    String contentType = determineContentType(uri);
    return new Response.Builder(statusCode)
                       .setHeader(MessageHeader.CONTENT_LENGTH, contentLength)
                       .setHeader(MessageHeader.CONTENT_TYPE, contentType)
                       .build();
  }
  
  private Response buildGetResponse(String uri) {
    int statusCode = determineStatusCode(uri);
    byte[] messageBody = createMessageBody(uri);
    int contentLength = messageBody.length;
    String contentType = determineContentType(uri);
    return new Response.Builder(statusCode)
                       .messageBody(messageBody)
                       .setHeader(MessageHeader.CONTENT_LENGTH, contentLength)
                       .setHeader(MessageHeader.CONTENT_TYPE, contentType)
                       .build();
  }
  
  private Response buildDeleteResponse() {
    return new Response.Builder(HttpStatusCode.NO_CONTENT)
                       .build();
  }

  private Response buildInternalServerErrorResponse() {
    return new Response.Builder(HttpStatusCode.INTERNAL_SERVER_ERROR)
                       .build();
  }

  private int determineStatusCode(String uri) {
    return this.store.existsInStore(uri) ? HttpStatusCode.OK : HttpStatusCode.NOT_FOUND;
  }

  private byte[] createMessageBody(String uri) {
    return this.store.existsInStore(uri) ? store.readFile(uri) : buildNotFoundMessage(uri).getBytes();
  }

  private String buildNotFoundMessage(String uri) {
    return uri + " was not found!";
  }

  private String determineContentType(String uri) {
    return this.store.existsInStore(uri) ? this.store.getFileType(uri) : "text/plain";
  } 

}
