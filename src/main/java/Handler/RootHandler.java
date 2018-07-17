import java.io.File;

public class RootHandler implements Handler {
  private String directoryPath;
  private Response response;
  
  public RootHandler(String directoryPath) {
    this.directoryPath = directoryPath;
  }
  
  public Response generateResponse(Request request) {
    int statusCode;
    String reasonPhrase;
    String messageBody = "";
    
    if (request.getMethod().equals("GET")) {
      statusCode = 200;
      reasonPhrase = "OK";
      messageBody = createMessageBody();
    } else {
      statusCode = 405;
      reasonPhrase = "Method Not Allowed";
    }

    return new Response.Builder()
                       .httpVersion("1.1")
                       .statusCode(statusCode)
                       .reasonPhrase(reasonPhrase)
                       .messageBody(messageBody)
                       .build();
  }

  public String createMessageBody() {
    String[] contentsOfDirectory = getContentsOfDirectory();
    return stringifyContentsOfDirectory(contentsOfDirectory);
  }

  private String[] getContentsOfDirectory() {
    File directory = new File(this.directoryPath);
    return directory.list();
  }

  private String stringifyContentsOfDirectory(String[] fileNames) {
    String content = "";
    
    if (fileNames.length == 0) {
      return "Empty directory!";
    }
    
    for (String fileName : fileNames) {
      content += fileName + "\n";
    }
    
    return content;
  }
  
}
