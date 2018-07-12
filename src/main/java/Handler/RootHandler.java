import java.io.File;

public class RootHandler implements Handler {
  private String directoryPath;
  private Response response;
  
  public RootHandler(String directoryPath) {
    this.directoryPath = directoryPath;
  }
  
  public Response generateResponse(Request request) {
    String messageBody = createMessageBody();

    return new Response.Builder()
                       .httpVersion("1.1")
                       .statusCode(200)
                       .reasonPhrase("OK")
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
