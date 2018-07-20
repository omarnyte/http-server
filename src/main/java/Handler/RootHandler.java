import java.io.File;

public class RootHandler implements Handler {
  private String directoryPath;
  private Response response;
  
  public RootHandler(String directoryPath) {
    this.directoryPath = directoryPath;
  }
  
  public Response generateResponse(Request request) {
    String method = request.getMethod();

    switch (method) {
      case "GET": 
        return new Response.Builder(HttpStatusCode.OK)
                           .messageBody(createMessageBody())
                           .build();
      default: 
        return new Response.Builder(HttpStatusCode.METHOD_NOT_ALLOWED)
                           .build();
    }
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
