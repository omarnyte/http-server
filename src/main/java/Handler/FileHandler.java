import java.io.UnsupportedEncodingException;

 public class FileHandler implements Handler {   
  private Directory directory;
  
  public FileHandler(Directory directory) {
    this.directory = directory;
  }
  
  public Response generateResponse(Request request) { 
    String uri = request.getURI();

    switch (request.getMethod()) { 
      case "GET":  
        return buildGetResponse(uri); 
      case "HEAD":  
        return buildHeadResponse(uri); 
      case "DELETE":  
        return this.directory.deleteFile(uri) ? buildDeleteResponse() : buildInternalServerErrorResponse();
      default:  
        return new Response.Builder(HttpStatusCode.METHOD_NOT_ALLOWED) 
                           .build(); 
    }
  }

  private Response buildGetResponse(String uri) {
    byte[] messageBody = this.directory.readFile(uri);
    int contentLength = messageBody.length;
    String contentType = this.directory.getFileType(uri);
    return new Response.Builder(HttpStatusCode.OK)
                       .messageBody(messageBody)
                       .setHeader(MessageHeader.CONTENT_LENGTH, contentLength)
                       .setHeader(MessageHeader.CONTENT_TYPE, contentType)
                       .build();
  }
  
  private Response buildHeadResponse(String uri) {
    byte[] messageBody = this.directory.readFile(uri);
    int contentLength = messageBody.length;
    String contentType = this.directory.getFileType(uri);
    return new Response.Builder(HttpStatusCode.OK)
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

}
