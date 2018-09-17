import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

 public class FileHandler implements Handler {         
  private static final List<String> SUPPORTED_METHODS = Arrays.asList( 
    HttpMethod.DELETE,
    HttpMethod.GET,
    HttpMethod.HEAD,
    HttpMethod.OPTIONS
  );
  
  private Directory directory;
  
  public FileHandler(Directory directory) {
    this.directory = directory;
  }
  
  public Response generateResponse(Request request) { 
    switch (request.getMethod()) { 
      case HttpMethod.OPTIONS: 
        List<String> supportedMethods = getSupportedMethods(request.getURI());
        return ResponseUtil.buildOptionsResponse(supportedMethods);
      case HttpMethod.GET:  
        return buildGetResponse(request.getURI()); 
      case HttpMethod.HEAD:  
        return buildHeadResponse(request.getURI()); 
      case HttpMethod.PUT:  
        return handlePutRequest(request);
      case HttpMethod.PATCH:  
        return handlePatchRequest(request);
      case HttpMethod.DELETE:  
        return handleDeleteRequest(request.getURI());
      default:  
        return new Response.Builder(HttpStatusCode.METHOD_NOT_ALLOWED) 
                           .build(); 
    }
  }

  private List<String> getSupportedMethods(String uri) {
    ArrayList<String> supportedMethods = new ArrayList<String>(SUPPORTED_METHODS);
    if (isPatchable(uri)) {
      supportedMethods.add(HttpMethod.PATCH);
    }
    if (isPutable(uri)) {
      supportedMethods.add(HttpMethod.PUT);
    }

    Collections.sort(supportedMethods);
    return supportedMethods;
  }

  private boolean isPatchable(String uri) {
    String fileType = this.directory.getFileType(uri);
    return fileType.equals(MimeType.JSON);
  }

  private boolean isPutable(String uri) {
    String fileType = this.directory.getFileType(uri);
    return fileType.equals(MimeType.JSON) || fileType.equals(MimeType.PLAIN_TEXT);
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

  public Response handlePutRequest(Request request) {
    byte[] content = request.getBody().getBytes();
    String uri = request.getURI();
    return this.directory.overwriteFileWithContent(uri, content) ? buildPutResponse(request) : buildInternalServerErrorResponse();
  }

  private Response buildPutResponse(Request request) {
    return new Response.Builder(HttpStatusCode.OK)
                       .build();
  }

  private Response handlePatchRequest(Request request) {
    String uri = request.getURI();
    String resourceContentType = this.directory.getFileType(uri);
    if (!PatchApplicator.isPatchable(resourceContentType)) {
      return buildMethodNotAllowedResponse();
    }

    try {
      return patchResource(request) ? buildGetResponse(uri) : buildInternalServerErrorResponse();
    } catch (BadRequestException e) {
      return buildBadRequestException();
    } catch (UnprocessableEntityException e) {
      return buildUnprocessableEntityResponse();
    } catch (UnsupportedMediaTypeException e) {
      return buildUnsupportedMediaTypeResponse();
    }
  }

  private boolean patchResource(Request request) throws BadRequestException, UnprocessableEntityException, UnsupportedMediaTypeException {
    String uri = request.getURI();
    String resourceContentType = this.directory.getFileType(uri);    
    String originalResourceContent = new String(this.directory.readFile(uri));
    PatchApplicator patchApplicator = PatchApplicator.getPatchApplicator(resourceContentType); 
    byte[] updatedResourceContent = patchApplicator.applyPatch(request, originalResourceContent).getBytes();
    return this.directory.overwriteFileWithContent(uri, updatedResourceContent);
  }

  private Response buildMethodNotAllowedResponse() {
    return new Response.Builder(HttpStatusCode.METHOD_NOT_ALLOWED)
                       .build();
  }

  private Response buildBadRequestException() {
    return new Response.Builder(HttpStatusCode.BAD_REQUEST)
                       .build();
  }

  private Response buildUnprocessableEntityResponse() {
    return new Response.Builder(HttpStatusCode.UNPROCESSABLE_ENTITY)
                       .build();
  }

  private Response buildUnsupportedMediaTypeResponse() {
    return new Response.Builder(HttpStatusCode.UNSUPPORTED_MEDIA_TYPE)
                       .build();
  }

  private Response handleDeleteRequest(String uri) {
    return this.directory.deleteFile(uri) ? buildDeleteResponse() : buildInternalServerErrorResponse();
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
