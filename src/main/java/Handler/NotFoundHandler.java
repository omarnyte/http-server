import java.io.File;
import java.io.IOException; 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
  
public class NotFoundHandler implements Handler {   
  private Directory directory;
  
  public NotFoundHandler(Directory directory) {
    this.directory = directory;
  }
  
  public Response generateResponse(Request request) {
    switch(request.getMethod()) {
      case HttpMethod.OPTIONS: 
        return handleOptionsRequest(request);
      case HttpMethod.PUT:
        return handlePutResponse(request);
      default:
        return buildNotFoundResponse(request.getURI());
    }
  }

  private Response handleOptionsRequest(Request request) {
    List<String> supportedMethods = getSupportedMethods(request.getURI());
    return ResponseUtil.buildOptionsResponse(supportedMethods);
  }

  private List<String> getSupportedMethods(String uri) {
    ArrayList<String> supportedMethods = new ArrayList<String>();
    supportedMethods.add(HttpMethod.OPTIONS);
    if (isPutable(uri)) supportedMethods.add(HttpMethod.PUT);
    return supportedMethods; 
  }

  private boolean isPutable(String uri) {
    String fileType = this.directory.getFileType(uri);
    return fileType.equals(MimeType.JSON) || fileType.equals(MimeType.PLAIN_TEXT);
  }

  private Response handlePutResponse(Request request) {
    return createFile(request) ? buildPutResponse() : buildInternalServerErrorResponse();
  }

  private boolean createFile(Request request) {
    byte[] fileContent = request.getBody().getBytes();
    return this.directory.createFileWithContent(request.getURI(), fileContent);
  }

  private Response buildPutResponse() {
    return new Response.Builder(HttpStatusCode.CREATED)
                       .messageBody(createPutMessageBody())
                       .build();
  }

  private String createPutMessageBody() {
    return "That file didn't exist before, so I've created it for you.";
  }

  private Response buildInternalServerErrorResponse() {
    return new Response.Builder(HttpStatusCode.INTERNAL_SERVER_ERROR)
                       .build();
  }
  
  private Response buildNotFoundResponse(String uri) {
    return new Response.Builder(HttpStatusCode.NOT_FOUND)
                       .messageBody(createNotFoundMessageBody(uri))
                       .build();
  }

  private String createNotFoundMessageBody(String uri) {
    return uri + " was not found!";
  }

}
