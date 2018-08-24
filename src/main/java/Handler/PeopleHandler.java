import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class PeopleHandler implements Handler {
  private final static String[] SUPPORTED_MEDIA_TYPES = { 
    MimeType.JSON, 
    MimeType.PLAIN_TEXT
  };

  private Directory directory;

  public PeopleHandler(Directory directory) {
    this.directory = directory;
  }

  public Response generateResponse(Request request) {
    switch (request.getMethod()) { 
      case "POST":  
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
    String uri = "/people/" + randomFileName(extension);
    this.directory.createFileWithContent(uri, content);
    return uri;
  }

  private String randomFileName(String extension) {
    int rand = new Random().nextInt(999999999) + 100000000;
    return Integer.toString(rand) + extension;
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
