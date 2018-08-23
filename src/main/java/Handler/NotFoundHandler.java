import java.io.File;
import java.io.IOException; 
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
  
public class NotFoundHandler implements Handler {   
  private Directory directory;
  
  public NotFoundHandler(Directory directory) {
    this.directory = directory;
  }
  
  public Response generateResponse(Request request) {
    switch(request.getMethod()) {
      case "PUT":
        return handlePutResponse(request);
      default:
        return buildNotFoundResponse(request.getURI());
    }
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
