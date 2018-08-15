import java.io.UnsupportedEncodingException;

 public class FileHandler implements Handler {   
  private Directory directory;
  
  public FileHandler(Directory directory) {
    this.directory = directory;
  }
  
  public Response generateResponse(Request request) { 
    String uri = request.getURI();

    switch (request.getMethod()) { 
      case "HEAD":  
        return buildHeadResponse(uri); 
      case "GET":  
        return buildGetResponse(uri); 
      case "DELETE":  
        return this.directory.deleteFile(uri) ? buildDeleteResponse() : buildInternalServerErrorResponse();
      default:  
        return new Response.Builder(HttpStatusCode.METHOD_NOT_ALLOWED) 
                           .build(); 
    } 
  }

  private Response buildNotFoundResponse(String uri) {
    return new Response.Builder(HttpStatusCode.NOT_FOUND)
                       .messageBody(buildNotFoundMessage(uri))
                       .build();
  private Response buildSubdirectoryResponse(Request request) {
    try {
      DataStore subdirectoryStore = this.store.createSubdirectoryStore(request.getURI());
      DirectoryHandler subdirectoryHandler = new DirectoryHandler(subdirectoryStore, request.getURI());
      return subdirectoryHandler.generateResponse(request);
    } catch (NonexistentDirectoryException e) {
      return new Response.Builder(HttpStatusCode.NOT_FOUND)
                         .build();
    }
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
    return this.directory.existsInStore(uri) ? HttpStatusCode.OK : HttpStatusCode.NOT_FOUND;
  }

  private byte[] createMessageBody(String uri) {
    return this.directory.existsInStore(uri) ? directory.readFile(uri) : buildNotFoundMessage(uri).getBytes();
  }

  private String buildNotFoundMessage(String uri) {
    return uri + " was not found!";
  }

  private String determineContentType(String uri) {
    return this.directory.existsInStore(uri) ? this.directory.getFileType(uri) : "text/plain";
  } 

}
