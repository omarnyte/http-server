import java.util.ArrayList;

public class PatchHandler implements Handler {
  private Directory directory;
  private JsonPatchParser jsonPatchParser;
  
  public PatchHandler(Directory directory, JsonPatchParser jsonPatchParser) {
    this.directory = directory;
    this.jsonPatchParser = jsonPatchParser;
  }  

  public Response generateResponse(Request request) {
    try {
      String requestContentType = request.getHeader(MessageHeader.CONTENT_TYPE);   
      if (!this.directory.existsInStore(request.getURI())) {
        return new Response.Builder(HttpStatusCode.NOT_FOUND)
                           .build();
      } else if (!requestContentType.equals(MimeType.JSON_PATCH)) {
        return new Response.Builder(HttpStatusCode.UNSUPPORTED_MEDIA_TYPE)
                           .setHeader(MessageHeader.ACCEPT_PATCH, MimeType.JSON_PATCH)
                           .build();
      }
  
      String updatedResourceContent = getUpdatedResourceContent(request);
      this.directory.overwriteFileWithContent(request.getURI(), updatedResourceContent.getBytes());
      return buildOkResponse(request);
    } catch (BadRequestException e) {
      return new Response.Builder(HttpStatusCode.BAD_REQUEST)
                         .build();
    } catch (UnprocessableEntityException e) {
      return new Response.Builder(HttpStatusCode.UNPROCESSABLE_ENTITY)
                         .build();
    }
    
  }

  private String getUpdatedResourceContent(Request request) throws BadRequestException, UnprocessableEntityException {
    String uri = request.getURI();
    String updatedResourceContent = new String(this.directory.readFile(uri));
    ArrayList<JsonPatchOperation> patches = this.jsonPatchParser.getOperations(request.getBody());
    for (JsonPatchOperation patch : patches) {
      updatedResourceContent = patch.applyOperation(updatedResourceContent);
    }

    return updatedResourceContent;
  }

  private Response buildOkResponse(Request request) {
    String uri = request.getURI();
    String fileContent = new String(this.directory.readFile(uri));
    return new Response.Builder(HttpStatusCode.OK)
                       .messageBody(fileContent)
                       .setHeader(MessageHeader.CONTENT_TYPE, MimeType.JSON)
                       .build();
  }

}
