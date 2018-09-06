import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class PeopleHandler implements Handler {
  private final static String[] SUPPORTED_MEDIA_TYPES = { 
    MimeType.JSON, 
    MimeType.PLAIN_TEXT
  };
  private static final List<String> SUPPORTED_METHODS = Arrays.asList( 
    HttpMethod.OPTIONS,
    HttpMethod.POST
  );

  private Directory directory;

  public PeopleHandler(Directory directory) {
    this.directory = directory;
  }

  public Response generateResponse(Request request) {
    switch (request.getMethod()) { 
      case HttpMethod.OPTIONS: 
        return ResponseUtil.buildOptionsResponse(SUPPORTED_METHODS);
      case HttpMethod.POST:  
        return handlePostRequest(request); 
      default:  
        return new Response.Builder(HttpStatusCode.METHOD_NOT_ALLOWED) 
                           .build(); 
    }    
  }

  private Response handlePostRequest(Request request) {
    String contentType = request.getHeader(MessageHeader.CONTENT_TYPE);
    if (Arrays.asList(SUPPORTED_MEDIA_TYPES).contains(contentType)) {
      String seeOtherUri = createFile(request);
      return buildPostResponse(seeOtherUri);
    } else {
      return buildUnsupportedMediaTypeResponse();
    }
  }

  private String createFile(Request request) {
    String contentType = request.getHeader(MessageHeader.CONTENT_TYPE);
    String extension = MimeType.getExtension(contentType);
    byte[] content = request.getBody().getBytes();
    String uri = "/people/" + Util.createRandomFileName(extension);
    this.directory.createFileWithContent(uri, content);
    return uri;
  }

  private Response buildPostResponse(String seeOtherUri) {
    return new Response.Builder(HttpStatusCode.CREATED)
                       .setHeader(MessageHeader.LOCATION, seeOtherUri)
                       .build();
  }

  private Response buildUnsupportedMediaTypeResponse() {
    return new Response.Builder(HttpStatusCode.UNSUPPORTED_MEDIA_TYPE)
                      .build();
  }

}
